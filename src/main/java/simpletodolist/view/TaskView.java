package simpletodolist.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import simpletodolist.Task;
import simpletodolist.controller.ToDoController;
import simpletodolist.model.ToDoModel;
import simpletodolist.webserver.ToDoSimpleWebServer;

/**
 * @author Dmitriy D
 */
public class TaskView extends ToDoHTMLView {

  private int editingIndex;

  public TaskView(int index, ToDoModel model, ToDoController controller) {
    super(model, controller);
    editingIndex = index;
  }

  public int getIndex() {
    return editingIndex;
  }

  public synchronized void createViewFromModel() {


    try {
      page = new ArrayList<>();
      Task task = model.getTaskByID(editingIndex);
      BufferedReader in = new BufferedReader(new InputStreamReader(TasksView.class.getClassLoader().getResourceAsStream("html/task1.html")));
      String line;
      while ((line = in.readLine()) != null) {
        if (line.contains("#num")) {
          line = line.replace("#num", String.valueOf(editingIndex));
          page.add(line);
        } else if (line.contains("#text")) {
          line = line.replace("#text", task.getText());
          page.add(line);
        } else if (line.contains("#entry")) {
          String[] lines = task.toHTML();
          for (String x : lines)
            page.add(x);
        } else
          page.add(line);
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
