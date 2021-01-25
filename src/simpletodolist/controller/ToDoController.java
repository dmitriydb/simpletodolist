
package simpletodolist.controller;

import simpletodolist.Task;
import simpletodolist.view.ToDoView;
import simpletodolist.model.ToDoModel;

/**
 *
 * @author Dmitriy D
 */
public interface ToDoController {
    public void addTask(Task t);
    public void removeTask(int index);
    public void updateTask(int index, Task newTask);
    public void changeView(ToDoView view);
    public void changeModel(ToDoModel model);
    
}
