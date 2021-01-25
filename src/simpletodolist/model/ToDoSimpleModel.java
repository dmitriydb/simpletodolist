
package simpletodolist.model;

import simpletodolist.Task;
import java.util.*;
import simpletodolist.ModelChangeListener;

/**
 *
 * @author Dmitriy D
 */
public class ToDoSimpleModel implements ToDoModel{
    private LinkedList<ModelChangeListener> modelListeners;
    private ArrayList<Task> taskList;
    
    public ToDoSimpleModel(){
        taskList = new ArrayList<Task>();
        modelListeners = new LinkedList<ModelChangeListener>();
    }
    
    public void addListener (ModelChangeListener listener){
        modelListeners.add(listener);
    }
    
    private void notifyAllListeners(){
        for (ModelChangeListener l : modelListeners){
            l.update();
        }
    } 
    
    public void addTask(Task t){
        taskList.add(t);
        notifyAllListeners();
    }
    
    public void removeTask (Task t){
        taskList.remove(t);
        notifyAllListeners();
    }

    public void removeTask (int index){
        taskList.remove(taskList.get(index));
        notifyAllListeners();
    }

    
    public void updateTask (int index, Task newTask){
        taskList.set(index, newTask);
        notifyAllListeners();
    }
    
    public Task[] getTasks(){
        Task[] array = new Task[taskList.size()];
        taskList.toArray(array);
        return array;
    }
    
}
