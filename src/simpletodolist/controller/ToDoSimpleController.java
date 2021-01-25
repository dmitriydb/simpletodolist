
package simpletodolist.controller;

import simpletodolist.Task;
import simpletodolist.view.ToDoView;
import simpletodolist.model.ToDoModel;
 /**
 * @author Dmitriy D
 */
public abstract class ToDoSimpleController implements ToDoController{
    protected ToDoView view;
    protected ToDoModel model;
    
    public ToDoSimpleController (ToDoModel model){
        this.model = model;
    }
    
    public void changeView(ToDoView view){
        this.view = view;
        view.createViewFromModel();
    }
    
    public void changeModel(ToDoModel model){
        this.model = model;
    }
    
       
    public void addTask(Task t) {
        model.addTask(t);
    }

    
    public void removeTask(int index) {
        model.removeTask(index);
    }

    
    public void updateTask(int index, Task newTask) {
        model.updateTask(index, newTask);
  }
    
    
}
