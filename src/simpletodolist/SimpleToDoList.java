
package simpletodolist;

import simpletodolist.controller.ToDoConsoleController;
import simpletodolist.controller.ToDoHTMLController;
import simpletodolist.model.ToDoSimpleModel;
import simpletodolist.view.ToDoConsoleListView;
import simpletodolist.view.ToDoHTMLListView;
import simpletodolist.webserver.ToDoSimpleWebServer;

/**
 *
 * @author Dmitriy D
 */
public class SimpleToDoList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ToDoSimpleModel model = new ToDoSimpleModel();
        model.addTask(new Task("Task 1"));
        model.addTask(new Task("Task 2"));
            
        ToDoHTMLController htmlController = new ToDoHTMLController(model);
        ToDoHTMLListView htmlView = new ToDoHTMLListView(model, htmlController);
        model.addListener(htmlView);
        
        //ToDoConsoleController controller = new ToDoConsoleController(model);
        //ToDoConsoleListView cview = new ToDoConsoleListView(model, controller);
        //model.addListener(cview);
        //cview.createViewFromModel();
        
        ToDoSimpleWebServer server = new ToDoSimpleWebServer(8080, model);
        
        server.run();
        
    }
    
    
}
