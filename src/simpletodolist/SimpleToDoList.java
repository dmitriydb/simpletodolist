
package simpletodolist;

import simpletodolist.controller.ToDoConsoleController;
import simpletodolist.model.ToDoSimpleModel;
import simpletodolist.view.ToDoConsoleListView;

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
        ToDoConsoleController controller = new ToDoConsoleController(model);
        ToDoConsoleListView cview = new ToDoConsoleListView(model, controller);
        model.addListener(cview);
        cview.createViewFromModel();
    }
    
    
}
