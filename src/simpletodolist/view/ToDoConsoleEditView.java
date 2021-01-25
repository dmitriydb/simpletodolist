
package simpletodolist.view;

import simpletodolist.Task;
import simpletodolist.controller.ToDoConsoleController;
import simpletodolist.model.ToDoModel;

/**
 *
 * @author Dmitriy D
 */
public class ToDoConsoleEditView extends ToDoConsoleView{
    
    private int editingTaskIndex;
    
    public ToDoConsoleEditView(int index, ToDoModel model, ToDoConsoleController controller){
        super(model, controller);
        editingTaskIndex = index;
    }
    
    @Override
    public void createViewFromModel(){
        System.out.println("=Editing task #" + (editingTaskIndex+1) + "=");
        System.out.println(model.getTasks()[editingTaskIndex].getDescription());
        System.out.print("NEW ENTRY=> ");
        handleEditConsoleLineInput();
    }
    
    public void update(){
        createViewFromModel();
    };
    
    public void handleEditConsoleLineInput(){
        ToDoConsoleController c = (ToDoConsoleController)controller;
        c.handleUserEdit(scanner.nextLine(), editingTaskIndex);
    }
    
    
}
