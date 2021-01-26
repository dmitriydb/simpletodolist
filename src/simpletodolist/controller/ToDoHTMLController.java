
package simpletodolist.controller;

import simpletodolist.Task;
import simpletodolist.model.ToDoModel;
import simpletodolist.view.ToDoHTMLEditView;
import simpletodolist.view.ToDoHTMLListView;

/**
 *
 * @author Dmitriy D
 */
public class ToDoHTMLController extends ToDoSimpleController{
    public ToDoHTMLController(ToDoModel model){
        super(model);
    }

    
    public ToDoHTMLEditView changeView(int index){
        ToDoHTMLEditView newView = new ToDoHTMLEditView(index, model, this);
        super.changeView(newView);
        return newView;
    }
    
    public ToDoHTMLListView getNewListView(){
        ToDoHTMLListView newView = new ToDoHTMLListView(model, this);
        newView.createViewFromModel();
        return newView;
    }
    
}
