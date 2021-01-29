
package simpletodolist.controller;

import simpletodolist.Task;
import simpletodolist.model.ToDoModel;
import simpletodolist.view.*;

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
    
    public UpdatesView getNewUpdatesView(){
        UpdatesView newView = new UpdatesView(model, this);
        newView.createViewFromModel();
        return newView;
    }
    
  
    
    public TasksView getNewTasksView(){
        TasksView newView = new TasksView(model, this);
        newView.createViewFromModel();
        return newView;
    }
    
    
}
