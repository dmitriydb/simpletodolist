package simpletodolist.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import simpletodolist.Item;
import simpletodolist.Task;
import simpletodolist.controller.ToDoController;
import simpletodolist.model.ToDoModel;
import simpletodolist.webserver.ToDoSimpleWebServer;
import simpletodolist.*;
/**
 *
 * @author Dmitriy D
 */
public class IssuesView extends ToDoHTMLView{
    
    public IssuesView(ToDoModel model, ToDoController controller ){
        super(model, controller);
    }
    
    public synchronized void createViewFromModel(){  
        try{
        page = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new FileReader("html/notes.html"));
            String line;
            while ((line = in.readLine()) != null){
           
                if (!line.startsWith("#list"))
                    page.add(line +"\n");
               else
                {
               int counter = 1;
               for (Item t : model.getItems()){
                if (t instanceof Issue){
                for (String line2 : t.toFullHTML(counter))
                    page.add(line2 + "\n");
                
                counter++;
                }
               }
           }        
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
