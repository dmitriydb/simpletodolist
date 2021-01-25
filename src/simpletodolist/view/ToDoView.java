
package simpletodolist.view;

import simpletodolist.ModelChangeListener;
import simpletodolist.controller.ToDoController;
import simpletodolist.model.ToDoModel;

/**
 *
 * @author Dmitriy D
 */
public abstract class ToDoView implements ModelChangeListener {
    protected ToDoModel model;
    protected ToDoController controller;
    
    
    public ToDoView(ToDoModel model, ToDoController controller){
        this.model = model;
        this.controller = controller;
    }
    
    public abstract void createViewFromModel();
    
}
