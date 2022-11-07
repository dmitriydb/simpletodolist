package simpletodolist.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import simpletodolist.Item;
import simpletodolist.Task;
import simpletodolist.controller.ToDoController;
import simpletodolist.model.ToDoModel;
import simpletodolist.webserver.ToDoSimpleWebServer;
import simpletodolist.*;

/**
 * @author Dmitriy D
 */
public class NotesView extends ToDoHTMLView {

  public NotesView(ToDoModel model, ToDoController controller) {
    super(model, controller);
  }

  public synchronized void createViewFromModel() {
    try {
      page = new ArrayList<>();
      BufferedReader in = new BufferedReader(new InputStreamReader(TasksView.class.getClassLoader().getResourceAsStream("html/notes.html")));
      String line;
      while ((line = in.readLine()) != null) {
        if (!line.startsWith("#list"))
          page.add(line + "\n");
        else {
          int counter = 1;
          for (Item t : model.getItems()) {
            if (t instanceof Note) {
              for (String line2 : t.toFullHTML())
                page.add(line2 + "\n");
              counter++;
            }
          }
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
