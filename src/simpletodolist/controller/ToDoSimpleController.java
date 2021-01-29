
package simpletodolist.controller;

import simpletodolist.Item;
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
    
       
    public void addItem(Item t) {
        model.addItem(t);
    }

    
    public void removeItem(int index) {
        model.removeItem(index);
    }

    
    public void updateItem(int index, Item newItem) {
        model.updateItem(index, newItem);
  }
    
    
}
