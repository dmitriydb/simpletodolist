package simpletodolist.model;

import simpletodolist.Task;
import java.util.*;
import simpletodolist.Item;
import simpletodolist.ModelChangeListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import simpletodolist.Issue;
import simpletodolist.Note;
import simpletodolist.Project;
import simpletodolist.TodoList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Dmitriy D
 */
public class ToDoSimpleModel implements ToDoModel {

  // JDBC driver name and database URL
  static final String JDBC_DRIVER = "org.postgresql.Driver";
  static final String DB_URL = "jdbc:postgresql://localhost:5432/todolist";

  //  Database credentials
  static final String USER = "todolist";
  static final String PASS = "todolist";

  private LinkedList<ModelChangeListener> modelListeners;
  private ArrayList<Item> itemList;
  private ArrayList<Project> projectLists;
  private ArrayList<TodoList> todoLists;

  private Connection conn;


  public ToDoSimpleModel() {
    try {
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }


    itemList = new ArrayList<Item>();
    modelListeners = new LinkedList<>();
    projectLists = new ArrayList<>();
    todoLists = new ArrayList<>();
  }


  public Project[] getProjects() {
    ArrayList<Project> result = new ArrayList<Project>();
    try {

      Connection conn;
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      Statement st = conn.createStatement();
      String query = "SELECT * from projects";
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        result.add(new Project(id, name));
      }


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    Project[] res = new Project[result.size()];
    result.toArray(res);
    return res;
  }

  public TodoList[] getLists() {
    ArrayList<TodoList> result = new ArrayList<TodoList>();
    try {

      Connection conn;
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      Statement st = conn.createStatement();
      String query = "SELECT * from lists";
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        result.add(new TodoList(id, name));
      }


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    TodoList[] res = new TodoList[result.size()];
    result.toArray(res);
    return res;
  }


  public void addListener(ModelChangeListener listener) {
    modelListeners.add(listener);
  }

  private void notifyAllListeners() {
    for (ModelChangeListener l : modelListeners) {
      l.update();
    }
  }

  public int getFirstListID() {
    try {
      Statement st = conn.createStatement();
      String query = "SELECT * from lists limit 1";
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int id = rs.getInt("id");
        return id;
      }

    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }

    return -1;
  }

  private int getTaskIDByTimeStamp(String time) {
    try {
      Statement st = conn.createStatement();
      String query = "SELECT id FROM tasks WHERE time = '" + time + "';";
      System.out.println(query);
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int id = rs.getInt("id");
        return id;
      }
    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }

    return -1;

  }

  public Task[] getTasksFromList(int id) {
    ArrayList<Task> result = new ArrayList<Task>();
    try {
      Statement st = conn.createStatement();
      String query = "SELECT tasks.id, tasks.text, tasks.time FROM tasks join tasks_in_lists on "
          + "tasks_in_lists.task_id = tasks.id"
          + " where tasks_in_lists.list_id = " + id + ";";
      System.out.println(query);
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int tid = rs.getInt("id");
        String text = rs.getString("text");
        String time = rs.getString("time");
        result.add(new Task(tid, text, time));
      }
    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }

    Task[] res = new Task[result.size()];
    result.toArray(res);
    return res;
  }


  private int getIssueIDByTimeStamp(String time) {
    try {
      Statement st = conn.createStatement();
      String query = "SELECT id FROM issues WHERE time = '" + time + "';";
      System.out.println(query);
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int id = rs.getInt("id");
        return id;
      }


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }

    return -1;

  }


  public void addItemToList(Item t, int listID) {
    try {
      String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
      t.setTimeStamp(currentTime);
      addItem(t);

      Statement st = conn.createStatement();
      if (t instanceof Task) {
        int ID = getTaskIDByTimeStamp(currentTime);
        System.out.println(ID + " -> " + listID);
        String query = "INSERT into tasks_in_lists (task_id, list_id) values (" + ID + "," + listID + ");";

        st.executeUpdate(query);
      } else if (t instanceof Issue) {

        int ID = getIssueIDByTimeStamp(currentTime);
        System.out.println(ID + " -> " + listID);
        String query = "INSERT into issues_in_projects (issue_id, project_id) values (" + ID + "," + listID + ");";
        st.executeUpdate(query);

      }


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }


  }


  public int getFirstProjectID() {
    try {
      Statement st = conn.createStatement();
      String query = "SELECT * from projects limit 1";
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int id = rs.getInt("id");
        return id;
      }

    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }

    return -1;
  }


  public void addItem(Item t) {
    String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    if (t.getTimeStamp() != null) timeStamp = t.getTimeStamp();

    if (t instanceof Note) {
      try {
        Note ti = (Note) t;
        if (ti.getTitle().length() > 50) return;
        Statement st = conn.createStatement();


        String query = "INSERT into notes (title, text, time) values ('" + ti.getTitle() + "','" + ti.getText() + "','" + timeStamp + "');";
        System.out.println(query);
        st.executeUpdate(query);
        st.close();
      } catch (SQLException ex) {
        Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
      }


    } else if (t instanceof Task) {
      try {
        Task ti = (Task) t;
        if (ti.getText().length() > 200) return;
        Statement st = conn.createStatement();
        String query = "INSERT into tasks (text, time) values ('" + ti.getText() + "','" + timeStamp + "');";
        st.executeUpdate(query);
        st.close();
      } catch (SQLException ex) {
        Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
      }


    } else if (t instanceof Issue) {
      try {
        Issue ti = (Issue) t;
        String status;
        if (ti.getStatus()) status = "1";
        else status = "0";
        if (ti.getText().length() > 10000) return;
        Statement st = conn.createStatement();
        String query = "INSERT into issues (status, text, time) values ('" + status + "','" + ti.getText() + "','" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()) + "');";
        st.executeUpdate(query);
        st.close();
      } catch (SQLException ex) {
        Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
      }


    }

    notifyAllListeners();
  }


  public void removeItem(Item t) {
    itemList.remove(t);
    notifyAllListeners();
  }

  public void removeItem(int index) {
    itemList.remove(itemList.get(index));
    notifyAllListeners();
  }


  public void updateItem(int index, Item newItem) {
    try {
      String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
      Statement st = conn.createStatement();
      String query = "";
      if (newItem instanceof Task)
        query = "UPDATE tasks SET text = '" + newItem.getText() + "', time = '" + currentTime + "'"
            + " WHERE id = " + index;

      if (newItem instanceof Issue) {
        Issue x = (Issue) newItem;
        int status = 0;
        if (x.getStatus()) status = 1;
        query = "UPDATE issues SET text = '" + newItem.getText() + "', time = '" +
            currentTime + "', status = " + status + " WHERE id = " + index;
      }

      if (newItem instanceof Note) {
        Note x = (Note) newItem;
        query = "UPDATE notes SET title = '" + x.getTitle() + "', text = '" + newItem.getText() + "', time = '" +
            currentTime + "' WHERE id = " + index;
      }

      st.executeUpdate(query);
      st.close();
      System.out.println(query);


      notifyAllListeners();
    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public Item[] getItems() {
    ArrayList<Item> result = new ArrayList<Item>();
    try {
      Statement st = conn.createStatement();
      String sql = "SELECT * from tasks";
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        String text = rs.getString("text");
        int id = rs.getInt("id");
        String timeStamp = rs.getString("time");
        result.add(new Task(id, text, timeStamp));

      }
      sql = "SELECT * from notes order by time desc";
      rs = st.executeQuery(sql);
      while (rs.next()) {
        try {
          String text = rs.getString("text");
          int id = rs.getInt("id");
          String timeStamp = rs.getString("time");
          String title = rs.getString("title");
          result.add(new Note(id, title, text, timeStamp));
        } catch (Exception ex) {

        }

      }

      sql = "SELECT * from issues";
      rs = st.executeQuery(sql);

      while (rs.next()) {
        try {
         String text = rs.getString("text");
          int id = rs.getInt("id");
          String timeStamp = rs.getString("time");
          Boolean isFixed = rs.getBoolean("status");
          result.add(new Issue(id, text, timeStamp, isFixed));
        } catch (Exception ex) {

        }
      }
      st.close();
    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }


    Item[] array = new Item[result.size()];
    result.toArray(array);
    return array;
  }

  @Override
  public void addProject(String name) {
    if (name.length() > 50) return;

    Connection conn = null;
    Statement stmt = null;
    try {

      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(
          DB_URL, USER, PASS);   // For MySQL only

      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from projects";
      ResultSet rs = stmt.executeQuery(sql);

      boolean hasEqual = false;
      while (rs.next()) {
        String prname = rs.getString("name");
        if (prname.equals(name)) hasEqual = true;
      }

      if (!hasEqual) {
        sql = "INSERT into projects (name) values ('" + name + "');";
        System.out.println(sql);
        stmt.executeUpdate(sql);
      }

      stmt.close();
      conn.close();


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @Override
  public void addList(String name) {
    if (name.length() > 50) return;

    Connection conn = null;
    Statement stmt = null;
    try {

      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(
          DB_URL, USER, PASS);   // For MySQL only

      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from lists";
      ResultSet rs = stmt.executeQuery(sql);

      boolean hasEqual = false;
      while (rs.next()) {
        String prname = rs.getString("name");
        if (prname.equals(name)) hasEqual = true;
      }

      if (!hasEqual) {
        sql = "INSERT into lists (name) values ('" + name + "');";
        stmt.executeUpdate(sql);
      }

      stmt.close();
      conn.close();


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }


  }

  @Override
  public Issue[] getIssuesFromProject(int id) {
    ArrayList<Issue> result = new ArrayList<Issue>();
    try {
      Statement st = conn.createStatement();
      String query = "SELECT issues.id, issues.text, issues.time, issues.status FROM issues join issues_in_projects on "
          + "issues_in_projects.issue_id = issues.id"
          + " where issues_in_projects.project_id = " + id + ";";
      System.out.println(query);
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        try {
          int tid = rs.getInt("id");
         String text = rs.getString("text");
          String time = rs.getString("time");
          int status = rs.getInt("status");
          Boolean sta = (status == 1) ? true : false;
          result.add(new Issue(tid, text, time, sta));
        } catch (Exception ex) {

        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    Issue[] res = new Issue[result.size()];
    result.toArray(res);
    return res;
  }

  @Override
  public Issue getIssueByID(int id) {
    try {
      Statement st = conn.createStatement();
      String query = "SELECT * FROM issues WHERE id = '" + id + "';";
      System.out.println(query);
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        try {
          int sid = rs.getInt("id");
          int statusint = rs.getInt("status");
          String time = rs.getString("time");
          String text = rs.getString("text");
          boolean status;
          if (statusint == 0) status = false;
          else status = true;
          return new Issue(sid, text, time, status);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  @Override
  public Task getTaskByID(int id) {
    try {
      Statement st = conn.createStatement();
      String query = "SELECT * FROM tasks WHERE id = '" + id + "';";
      System.out.println(query);
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int sid = rs.getInt("id");
        String time = rs.getString("time");
        String text = rs.getString("text");
        boolean status;
        return new Task(sid, text, time);
      }


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;

  }

  @Override
  public Note getNoteByID(int id) {
    try {
      Statement st = conn.createStatement();
      String query = "SELECT * FROM notes WHERE id = '" + id + "';";
      System.out.println(query);
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        try {
          int sid = rs.getInt("id");
          String time = rs.getString("time");
          String text = rs.getString("text");
          String title = rs.getString("title");
          return new Note(sid, title, text, time);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;


  }

  @Override
  public int getProjectIDByIssueID(int id) {
    try {
      Statement st = conn.createStatement();
      String query = "SELECT project_id from issues_in_projects where issue_id = " + id;
      System.out.println(query);
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int prID = rs.getInt("project_id");
        return prID;
      }

    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }

    return -1;
  }

  @Override
  public int getListIDByTaskID(int id) {
    try {
      Statement st = conn.createStatement();
      String query = "SELECT list_id from tasks_in_lists where task_id = " + id;
      System.out.println(query);
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        int prID = rs.getInt("list_id");
        return prID;
      }

    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }

    return -1;

  }

  @Override
  public void removeTask(int id) {
    try {
      Statement st = conn.createStatement();
      String query = "delete from tasks_in_lists where task_id = " + id;
      System.out.println(query);
      st.executeUpdate(query);
      query = "delete from tasks where id = " + id;
      System.out.println(query);
      st.executeUpdate(query);


    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @Override
  public void removeNote(int id) {
    try {
      Statement st = conn.createStatement();
      String query = "delete from notes where id = " + id;
      System.out.println(query);
      st.executeUpdate(query);
    } catch (SQLException ex) {
      Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
    }
  }


}
