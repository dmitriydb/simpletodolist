package simpletodolist;

import simpletodolist.controller.ToDoHTMLController;
import simpletodolist.model.ToDoSimpleModel;
import simpletodolist.view.*;
import simpletodolist.webserver.ToDoSimpleWebServer;

/**
 * @author Dmitriy D
 */
public class SimpleToDoList {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    ToDoSimpleModel model = new ToDoSimpleModel();
    ToDoHTMLController htmlController = new ToDoHTMLController(model);
    UpdatesView htmlView = new UpdatesView(model, htmlController);
    model.addListener(htmlView);
    ToDoSimpleWebServer server = new ToDoSimpleWebServer(8081, model);
    server.run();
  }


}
