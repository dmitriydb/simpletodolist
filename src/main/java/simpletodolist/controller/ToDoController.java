package simpletodolist.controller;

import simpletodolist.Item;
import simpletodolist.view.ToDoView;

/**
 * @author Dmitriy D
 */
public interface ToDoController {

  public void updateItem(int index, Item newItem);

  public void changeView(ToDoView view);

}
