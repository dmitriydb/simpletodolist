
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
public class ToDoHTMLListView extends ToDoView{
    
    public ToDoHTMLListView(ToDoModel model, ToDoController controller ){
        super(model, controller);
    }
    
    public void createViewFromModel(){
        
        
        try{
        ArrayList<String> page = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new FileReader("html/index.html"));
            String line;
            while ((line = in.readLine()) != null){
                if (!line.startsWith("#list"))
                    page.add(line);
               else
                {
               int counter = 1;
               for (Task t : model.getTasks()){
                page.add("<tr>\n" +
                "<th><p>" + counter + ": " + t.getDescription() + "</p></th>\n" +
                "<th><form method=\"get\" action=\"/edit" + counter + "\">\n" +
                  "<button type=\"submit\">Edit</button>\n" +
                  "</form>\n" +
                  "</th>\n" +
                  "<th><form method=\"get\" action=\"/remove" + counter + "\">\n" +
                  "<button type=\"submit\">Remove</button>\n" +
                  "</form>\n" +
                  "</th>\n" +
              "</tr>\n");
                counter++;
               }
               
                }
               
            }
            in.close();
            String[] response = new String[page.size()];
            page.toArray(response);
            ToDoSimpleWebServer.response = response;
            
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    public void update(){
        
    }
    
}
