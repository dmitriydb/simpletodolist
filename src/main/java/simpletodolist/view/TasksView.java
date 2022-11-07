package simpletodolist.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import simpletodolist.Item;
import simpletodolist.Task;
import simpletodolist.TodoList;
import simpletodolist.controller.ToDoController;
import simpletodolist.model.ToDoModel;

/**
 * @author Dmitriy D
 */
public class TasksView extends ToDoHTMLView {

  private int listID = -1;

  public TasksView(ToDoModel model, ToDoController controller) {
    super(model, controller);
    listID = model.getFirstListID();
  }

  public TasksView(int listID, ToDoModel model, ToDoController controller) {
    super(model, controller);
    this.listID = listID;
  }


  public synchronized void createViewFromModel() {
    try {
      page = new ArrayList<String>();
      BufferedReader in = new BufferedReader(new InputStreamReader(TasksView.class.getClassLoader().getResourceAsStream("html/todolist.html")));
      String line;
      while ((line = in.readLine()) != null) {
        if (line.contains("#listnum")) {
          line = line.replace("#listnum", String.valueOf(listID));
          page.add(line);
        } else if (line.startsWith("#listlist")) {
          for (TodoList p : model.getLists()) {

            for (String line2 : p.toHTML()) {
              page.add(line2);
            }
          }
        } else if (line.startsWith("#list")) {

          for (Task t : model.getTasksFromList(listID)) {

            for (String line2 : t.toFullHTML()) {
              page.add(line2);
            }
          }
        } else {
          page.add(line);

        }
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  public void update() {
    createViewFromModel();
  }

}
