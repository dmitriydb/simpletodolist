
package simpletodolist.view;

import java.util.Scanner;
import simpletodolist.Task;
import simpletodolist.model.ToDoModel;
import simpletodolist.controller.ToDoConsoleController;


/**
 *
 * @author Dmitriy D
 */
public class ToDoConsoleListView extends ToDoConsoleView{
    
     public ToDoConsoleListView(ToDoModel model, ToDoConsoleController controller){
        super(model, controller);
    }
    
    @Override
    public void createViewFromModel(){
        System.out.println("=TO DO LIST=");
        Task[] tasks = model.getTasks();
        int counter = 1;
        for (Task t : tasks){
            System.out.println(counter++ + ": " + t.getDescription());
        }
        handleNewConsoleInputLine();
    }
    
    public void update(){
        createViewFromModel();
    };
    
    private void handleNewConsoleInputLine(){
       System.out.print("> ");
       ToDoConsoleController c = (ToDoConsoleController)controller;
       c.handleUserInput(scanner.nextLine());
    } 
    
    
}
