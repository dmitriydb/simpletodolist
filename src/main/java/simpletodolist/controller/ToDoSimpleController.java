package simpletodolist.controller;

import simpletodolist.Item;
import simpletodolist.model.ToDoModel;
import simpletodolist.view.ToDoView;

/**
 * @author Dmitriy D
 */
public abstract class ToDoSimpleController implements ToDoController {
  protected ToDoView view;
  protected ToDoModel model;

  public ToDoSimpleController(ToDoModel model) {
    this.model = model;
  }

  public void changeView(ToDoView view) {
    this.view = view;
    view.createViewFromModel();
  }

  public void updateItem(int index, Item newItem) {
    model.updateItem(index, newItem);
  }

}
