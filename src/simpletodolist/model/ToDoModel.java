
package simpletodolist.model;

import simpletodolist.Item;
import simpletodolist.Project;
import simpletodolist.Task;

/**
 *
 * @author Dmitriy D
 */
public interface ToDoModel {
    public void addItem (Item t);
    public void removeItem (Item t);
    public void removeItem (int index);
    public void updateItem (int index, Item newItem);
    public Item[] getItems();
    public void addProject(String name);
    public void addList(String name);
    public Project[] getProjects();
}
