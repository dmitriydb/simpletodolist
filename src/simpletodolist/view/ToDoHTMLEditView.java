
package simpletodolist.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import simpletodolist.Task;
import simpletodolist.controller.ToDoController;
import simpletodolist.model.ToDoModel;
import simpletodolist.webserver.ToDoSimpleWebServer;

/**
 *
 * @author Dmitriy D
 */
public class ToDoHTMLEditView extends ToDoHTMLView{
    
    private int editingIndex;
    
    public ToDoHTMLEditView(int index, ToDoModel model, ToDoController controller ){
        super(model, controller);
        editingIndex = index;
    }
    
    public int getIndex(){
        return editingIndex;
    }
    
    public synchronized void createViewFromModel(){
        
     
        try{
        page = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new FileReader("html/edit.html"));
            String line;
            while ((line = in.readLine()) != null){
                  line = line.replace("#old", model.getTasks()[editingIndex].getDescription());
                  line = line.replace("#num", String.valueOf((editingIndex + 1)));
                  page.add(line);
            }
            in.close();
         
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    public void update(){
        createViewFromModel();
    }
    
}
