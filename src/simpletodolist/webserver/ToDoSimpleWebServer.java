
package simpletodolist.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
/**
 *
 * @author Dmitriy D
 */
public class ToDoSimpleWebServer {
    private ServerSocket serverSocket;
    private int port;
    
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
        while ((c = in.readLine()) != null){
           if (c.startsWith("GET")){
               String[] tokens = c.split(" ");
               String resource = tokens[1];
               
               
               System.out.println(resource);
           }
        }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
