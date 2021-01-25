
package simpletodolist.model;

import simpletodolist.Task;

/**
 *
 * @author Dmitriy D
 */
public interface ToDoModel {
    public void addTask (Task t);
    public void removeTask (Task t);
    public void removeTask (int index);
    public void updateTask (int index, Task newTask);
    public Task[] getTasks();
}
