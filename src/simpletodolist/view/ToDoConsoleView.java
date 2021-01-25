
package simpletodolist.view;

import java.util.Scanner;
import simpletodolist.controller.ToDoConsoleController;
import simpletodolist.model.ToDoModel;

/**
 *
 * @author Dmitriy D
 */
public abstract class ToDoConsoleView extends ToDoView{
    protected Scanner scanner;
    
    public ToDoConsoleView(ToDoModel model, ToDoConsoleController controller){
        super(model, controller);
        scanner = new Scanner(System.in);
    }
    
    
}
