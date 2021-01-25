
package simpletodolist.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.*;
import simpletodolist.Task;
import simpletodolist.controller.ToDoController;
import simpletodolist.controller.ToDoHTMLController;
import simpletodolist.view.ToDoHTMLEditView;
import simpletodolist.view.ToDoView;
/**
 *
 * @author Dmitriy D
 */
public class ToDoSimpleWebServer {
    private ServerSocket serverSocket;
    private int port;
    public static String[] response;
    private ToDoHTMLController controller;
    private ToDoView view;
    
    
    public void setController(ToDoHTMLController controller){
        this.controller = controller;
    }
    
    public void setView(ToDoView view){
        this.view = view;
    }
    
    public ToDoSimpleWebServer(int port){
        try{
        serverSocket = new ServerSocket(port);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void run(){
        while (true){
            try{
            Socket client = serverSocket.accept();
                handleClientSession(client);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void handleClientSession(Socket client){
        try{
        
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String c;
        OutputStream os = client.getOutputStream();
        PrintStream out = new PrintStream(os);
        
        
        while ((c = in.readLine()) != null){
           if (c.startsWith("GET")){
               String[] tokens = c.split(" ");
               String resource = tokens[1];
               
               if (resource.equals("/") || resource.equals("/index.html")){
                System.out.println(resource);
               
                out.println("HTTP/1.1 200 OK");
                out.println("");
    
                view.createViewFromModel();
                
                for (String line : response){
                    out.println(line);
                }
                
               
                out.close();
               }
               else
               
               if (resource.startsWith("/add?name=")){
                   String name = resource.substring(10);
                   String result = java.net.URLDecoder.decode(name);
                   System.out.println(result);
                   controller.addTask(new Task(result));
                 
                   out.println("HTTP/1.1 200 OK");
                   out.println("");
    
                   view.createViewFromModel();
                
                for (String line : response){
                    out.println(line);
                }         
                out.close();
               }
               else
              if (resource.startsWith("/edit")){
                  String name = resource.substring(5);
                  view = controller.changeView(Integer.valueOf(name) - 1);
                  
                  out.println("HTTP/1.1 200 OK");
                  out.println("");
                  
                  for (String line : response){
                    out.println(line);
                }         
                out.close();
              }
               else
              if (resource.startsWith("/remove")){
                  String name = resource.substring(7);
                   String result = java.net.URLDecoder.decode(name);
                  controller.removeTask(Integer.valueOf(result) - 1);
                  view.createViewFromModel();
                  out.println("HTTP/1.1 200 OK");
                  out.println("");
                  
                  for (String line : response){
                    out.println(line);
                }         
                out.close();
              }
              
               else
                  if (resource.startsWith("/save?name=")){
                   String name = resource.substring(11);
                   String result = java.net.URLDecoder.decode(name);
                   //System.out.println(result);
                   
                   ToDoHTMLEditView v = (ToDoHTMLEditView)view;
                   int index = v.getIndex();
                   controller.updateTask(index, new Task(result));
                   
                   view = controller.getNewListView();
                   
                   
                   out.println("HTTP/1.1 200 OK");
                   out.println("");
    
                   view.createViewFromModel();
                
                for (String line : response){
                    out.println(line);
                }         
                out.close();
               }
               
           }
        
             //      System.out.println(c);
             
        }
        in.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
