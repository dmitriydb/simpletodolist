
package simpletodolist;

import simpletodolist.controller.ToDoHTMLController;
import simpletodolist.model.ToDoSimpleModel;
import simpletodolist.view.*;
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
        model.addItem(new Task("Task 1"));
        model.addItem(new Task("Task 2"));
        model.addItem(new Issue("Issue 1", false));
        model.addItem(new Issue("Issue 2", true));
        model.addItem(new Note("Note tutle1", "fgfdkjghdfkghdfkg"));
        model.addItem(new Note("Note title2","fghdfjgh"));
        
            
        ToDoHTMLController htmlController = new ToDoHTMLController(model);
        UpdatesView htmlView = new UpdatesView(model, htmlController);
        model.addListener(htmlView);
        
        //ToDoConsoleController controller = new ToDoConsoleController(model);
        //ToDoConsoleListView cview = new ToDoConsoleListView(model, controller);
        //model.addListener(cview);
        //cview.createViewFromModel();
        
        ToDoSimpleWebServer server = new ToDoSimpleWebServer(8080, model);
        
        server.run();
        
    }
    
    
}
