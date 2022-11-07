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
public class UpdateView extends ToDoHTMLView {

  private int editingIndex;

  public UpdateView(int index, ToDoModel model, ToDoController controller) {
    super(model, controller);
    editingIndex = index;
  }

  public int getIndex() {
    return editingIndex;
  }

  public synchronized void createViewFromModel() {
    try {
      page = new ArrayList<>();
      BufferedReader in = new BufferedReader(new InputStreamReader(TasksView.class.getClassLoader().getResourceAsStream("html/update1.html")));
      String line;
      while ((line = in.readLine()) != null) {
        if (line.contains("#entry")) {
          String[] lines = model.getItems()[editingIndex].toHTML();
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
