
package simpletodolist.controller;

import simpletodolist.Item;
import simpletodolist.Task;
import simpletodolist.view.ToDoView;
import simpletodolist.model.ToDoModel;

/**
 *
 * @author Dmitriy D
 */
public interface ToDoController {
    public void addItem(Item t);
    public void removeItem(int index);
    public void updateItem(int index, Item newItem);
    public void changeView(ToDoView view);
    public void changeModel(ToDoModel model);
    
}
