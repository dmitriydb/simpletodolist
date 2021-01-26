
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
import simpletodolist.model.ToDoModel;
import simpletodolist.view.ToDoHTMLEditView;
import simpletodolist.view.ToDoHTMLListView;
import simpletodolist.view.ToDoHTMLView;
/**
 *
 * @author Dmitriy D
 */
public class ToDoSimpleWebServer{
    private ServerSocket serverSocket;
    private int port;
    private ToDoModel model;
    
    public ToDoSimpleWebServer(int port, ToDoModel model){
        this.model = model;
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
                new Thread(new ServerHandler(client)).start();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private class ServerHandler implements Runnable{
        
        private ToDoHTMLController controller;
        private ToDoHTMLView view;
    
    

        private Socket client;
        ServerHandler(Socket client){
            this.client = client;
            this.controller = new ToDoHTMLController(model);
            this.view = new ToDoHTMLListView(model, controller);
            
        }
        
        public void run(){
            handleClientSession();
        }
        
        private synchronized void handleClientSession(){
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
               
                view = controller.getNewListView();
                view.createViewFromModel();
                out.println("HTTP/1.1 200 OK");
                out.println("");
    
                String[] response = view.getView();
                
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
                                   
                   view.createViewFromModel();
                   
                   out.println("HTTP/1.1 200 OK");
                   out.println("");
    
                String[] response = view.getView();
               
                for (String line : response){
                    out.println(line);
                }         
                out.close();
               }
               else
              if (resource.startsWith("/edit")){
                  String name = resource.substring(5);
                  this.view = controller.changeView(Integer.valueOf(name) - 1);
                  
                  this.view.createViewFromModel();
                  out.println("HTTP/1.1 200 OK");
                  out.println("");
   
                  String[] response = view.getView();
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
                  
                  String[] response = view.getView();
                  for (String line : response){
                    out.println(line);
                }         
                out.close();
              }
              
               else
                  if (resource.startsWith("/save")){
                   int lastIndex = resource.indexOf("?");
                   int index = Integer.valueOf(resource.substring(5, lastIndex));
                   
                   int index2 = resource.indexOf("=");
                   String name = resource.substring(index2 + 1) ;
                   
                   String result = java.net.URLDecoder.decode(name);
                            
                   System.out.println(index +":" + result);
                   controller.updateTask(index - 1, new Task(result));
                   
                   view = controller.getNewListView();
                   view.createViewFromModel();
                   
                   out.println("HTTP/1.1 200 OK");
                   out.println("");
    
                    String[] response = view.getView();
                    for (String line : response){
                    out.println(line);
                }         
                out.close();
               }
               
           }
           
        }
        in.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    }
    
    
}
