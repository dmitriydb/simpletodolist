
package simpletodolist.view;

import java.util.ArrayList;
import simpletodolist.controller.ToDoController;
import simpletodolist.model.ToDoModel;

/**
 *
 * @author Dmitriy D
 */
public abstract class ToDoHTMLView extends ToDoView{
    
    protected volatile ArrayList<String> page;
    
    public synchronized String[] getView(){
        return page.toArray(new String[page.size()]);
    }
    
    public ToDoHTMLView (ToDoModel model, ToDoController controller){
        super(model, controller);
    }
    
}
