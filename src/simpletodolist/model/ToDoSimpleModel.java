
package simpletodolist.model;

import simpletodolist.Task;
import java.util.*;
import simpletodolist.Item;
import simpletodolist.ModelChangeListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import simpletodolist.Project;
import simpletodolist.TodoList;
/**
 *
 * @author Dmitriy D
 */
public class ToDoSimpleModel implements ToDoModel{
    
    // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/todolistbase";

   //  Database credentials
   static final String USER = "todolist";
   static final String PASS = "todolist";
   
    
    private LinkedList<ModelChangeListener> modelListeners;
    private ArrayList<Item> itemList;
    private ArrayList<Project> projectLists;
    private ArrayList<TodoList> todoLists;
    
    public ToDoSimpleModel(){
        itemList = new ArrayList<Item>();
        modelListeners = new LinkedList<ModelChangeListener>();
        
        projectLists = new ArrayList<Project>();
        todoLists = new ArrayList<TodoList>();
    }
    
    
    public Project[] getProjects(){
       
        ArrayList<Project> result = new ArrayList<Project>();
       try {
           
           Connection conn;
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           Statement st = conn.createStatement();
           String query = "SELECT * from projects";
           ResultSet rs = st.executeQuery(query);
           while (rs.next()){
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
    
    public void addListener (ModelChangeListener listener){
        modelListeners.add(listener);
    }
    
    private void notifyAllListeners(){
        for (ModelChangeListener l : modelListeners){
            l.update();
        }
    } 
    
    public void addItem(Item t){
        itemList.add(t);
        notifyAllListeners();
    }
    
    public void removeItem (Item t){
        itemList.remove(t);
        notifyAllListeners();
    }

    public void removeItem (int index){
        itemList.remove(itemList.get(index));
        notifyAllListeners();
    }

    
    public void updateItem (int index, Item newItem){
        itemList.set(index, newItem);
        notifyAllListeners();
    }
    
    public Item[] getItems(){
        Item[] array = new Item[itemList.size()];
        itemList.toArray(array);
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
               "jdbc:mysql://localhost:3306/todolistbase?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "todolist", "todolist");   // For MySQL only
          
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from projects";
      ResultSet rs = stmt.executeQuery(sql);
      
      boolean hasEqual = false;
      while(rs.next()){
         String prname = rs.getString("name");
         if (prname.equals(name)) hasEqual = true;
      }
      
      if (!hasEqual){
      sql = "INSERT into projects (name) values ('"+name+"');";
      stmt.executeUpdate(sql);}
      
      stmt.close();
      conn.close();      
      
      
       } catch (SQLException ex) {
           Logger.getLogger(ToDoSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
       }
              
    }

    @Override
    public void addList(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
