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
public class IssuesView extends ToDoHTMLView {

  private int projectID = -1;

  public IssuesView(ToDoModel model, ToDoController controller) {
    super(model, controller);
    projectID = model.getFirstProjectID();
  }

  public IssuesView(int projectID, ToDoModel model, ToDoController controller) {
    super(model, controller);
    this.projectID = projectID;
  }


  public synchronized void createViewFromModel() {
    try {
      page = new ArrayList<String>();
      BufferedReader in = new BufferedReader(new InputStreamReader(TasksView.class.getClassLoader().getResourceAsStream("html/bugtracker.html")));
      String line;
      while ((line = in.readLine()) != null) {
        line = line.replace("#listnum", String.valueOf(projectID));
        if (line.startsWith("#list")) {
          for (Item t : model.getIssuesFromProject(projectID)) {
            for (String line2 : t.toFullHTML()) {
              page.add(line2 + "\n");
            }
          }
        } else if (line.startsWith("#projectlist")) {
          for (Project p : model.getProjects()) {

            for (String line2 : p.toHTML()) {
              page.add(line2 + "\n");
            }
          }
        } else {
          page.add(line + "\n");
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
