
package simpletodolist.controller;

import simpletodolist.Task;
import simpletodolist.model.ToDoModel;
import simpletodolist.view.ToDoConsoleEditView;
import simpletodolist.view.ToDoConsoleListView;
import simpletodolist.view.ToDoView;

/**
 *
 * @author Dmitriy D
 */
public class ToDoConsoleController extends ToDoSimpleController{
    
    public ToDoConsoleController (ToDoModel model){
        super(model);
    }

    @Override
    public void addTask(Task t) {
        model.addTask(t);
    }

    @Override
    public void removeTask(int index) {
        model.removeTask(index);
    }

    @Override
    public void updateTask(int index, Task newTask) {
        model.updateTask(index, newTask);
  }
    
    public void handleUserInput(String line){
        
        if (line.startsWith("add")){
            String input = line.substring(line.indexOf(' '));
            model.addTask(new Task(input));
        }
        else 
        if (line.startsWith("remove")){
            String[] tokens = line.split("\\s+");
            model.removeTask(Integer.valueOf(tokens[1]) - 1);
        }
        if (line.startsWith("edit")){
            String[] tokens = line.split("\\s+");
            int index = Integer.valueOf(tokens[1]) - 1;
            changeView(new ToDoConsoleEditView(index, model, this));
            
        }
            
       
        
    }
    
    public void handleUserEdit(String line, int index){
        model.updateTask(index, new Task(line));
        changeView(new ToDoConsoleListView(model, this));
    }
    
}
