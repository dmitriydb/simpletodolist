
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
import java.util.ArrayList;
import java.util.List;
import simpletodolist.Task;
import simpletodolist.controller.ToDoController;
import simpletodolist.controller.ToDoHTMLController;
import simpletodolist.model.ToDoModel;
import simpletodolist.view.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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
            this.view = new UpdatesView(model, controller);
            
        }
        
        public void run(){
            handleClientSession();
        }
        
        private synchronized void showView(ToDoHTMLView view){
           
            try{
          
         OutputStream os = client.getOutputStream();
        PrintStream out = new PrintStream(os);
         this.view = view;
         view.createViewFromModel();
        out.println("HTTP/1.1 200 OK");
        out.println("");
    
                
                String[] response = view.getView();
              
                for (String line : response){
                    out.println(line);
                }
                out.close();
                os.close();
               client.close();
               
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
           
            
            
        }
        
        private synchronized void handleClientSession(){
        try{
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        
        String c;
        String postResource = null;
        
        boolean ispost=false;
        int contentL = 0;
        while ((c = in.readLine()) != null && (c.length() != 0)) {
       
          
           if (c.startsWith("GET")){
               String[] tokens = c.split(" ");
               String resource = tokens[1];
             
               if (resource.equals("/") || resource.equals("/index.html"))
                   showView(new UpdatesView(model, controller));
                else
              
               if (resource.equals("/notes.html"))
                   showView(new NotesView(model, controller));
                else
               if (resource.equals("/bugtracker.html"))
                   showView(new IssuesView(model, controller));
                else
                         if (resource.equals("/todolist.html"))
                   showView(new TasksView(model, controller));
               
                 else  if (resource.startsWith("/update")){
                  String name = resource.substring(7, resource.indexOf('.'));
                showView(new UpdateView(Integer.valueOf(name) - 1, model, controller));
              }
                 else  if (resource.startsWith("/task")){
                  String name = resource.substring(5, resource.indexOf('.'));
                showView(new TaskView(Integer.valueOf(name) - 1, model, controller));
              }
                 else  if (resource.startsWith("/issue")){
                  String name = resource.substring(6, resource.indexOf('.'));
                showView(new IssueView(Integer.valueOf(name) - 1, model, controller));
              }
                 else  if (resource.startsWith("/note")){
                  String name = resource.substring(5, resource.indexOf('.'));
                showView(new NoteView(Integer.valueOf(name) - 1, model, controller));
              }
               
               break;
               }
            
           else
                 if (c.startsWith("POST")){
                     ispost=true;
                     String[] tokens = c.split(" ");
                     postResource = tokens[1];
                 }
           else
                     if (c.startsWith("Content-Length")){
                         String[] x= c.split(":");
                         contentL = Integer.valueOf(x[1].trim());
                         System.out.println(contentL);
                     }
           
           
          

        }
        
        
       if (ispost){
    
       StringBuilder payload = new StringBuilder();
        while(in.ready()){
            payload.append((char) in.read());
            }
        String coded = new String(payload);
        String result = URLDecoder.decode(coded);
        Map<String,String> postFieldsMap = new HashMap<String, String>();
        String postFields[] = result.trim().split("&");
        for (String postField : postFields){
            String[] values = postField.split("=");
            String key = values[0].trim();
            String value = values[1].trim();
            postFieldsMap.put(key, value);
        }
        System.out.println(postFieldsMap);
        if (postResource.equals("/addproject")){
            model.addProject(postFieldsMap.get("projectname"));
            showView(new IssuesView(model, controller));
        }
        
       }
       in.close();
       
       
        }
        
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
      
        
        private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
    }
    
   }
   /*
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
               }*/
        