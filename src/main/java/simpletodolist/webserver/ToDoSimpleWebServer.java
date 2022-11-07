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
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import simpletodolist.Issue;
import simpletodolist.Note;

/**
 * @author Dmitriy D
 */
public class ToDoSimpleWebServer {
  private ServerSocket serverSocket;
  private int port;
  private ToDoModel model;

  public ToDoSimpleWebServer(int port, ToDoModel model) {
    this.model = model;
    try {
      serverSocket = new ServerSocket(port);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  public void run() {
    while (true) {
      try {
        Socket client = serverSocket.accept();
        new Thread(new ServerHandler(client)).start();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private class ServerHandler implements Runnable {

    private ToDoHTMLController controller;
    private ToDoHTMLView view;


    private Socket client;

    ServerHandler(Socket client) {
      this.client = client;
      this.controller = new ToDoHTMLController(model);
      this.view = new UpdatesView(model, controller);

    }

    public void run() {
      handleClientSession();
    }

    private synchronized void showView(ToDoHTMLView view) {

      try {

        OutputStream os = client.getOutputStream();
        PrintStream out = new PrintStream(os);
        this.view = view;
        view.createViewFromModel();
        out.println("HTTP/1.1 200 OK");
        out.println("");
        String[] response = view.getView();
        for (String line : response) {
          out.println(line);
        }
        out.close();
        os.close();
        client.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    private synchronized void handleClientSession() {
      try {
        System.out.println("Новый клиент " + client.getInetAddress());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String c;
        String postResource = null;

        boolean ispost = false;
        int contentL = 0;
        while ((c = in.readLine()) != null && (c.length() != 0)) {


          if (c.startsWith("GET")) {
            String[] tokens = c.split(" ");
            String resource = tokens[1];

            if (resource.equals("/") || resource.equals("/index.html"))
              showView(new UpdatesView(model, controller));
            else if (resource.equals("/notes.html"))
              showView(new NotesView(model, controller));
            else if (resource.equals("/bugtracker.html"))
              showView(new IssuesView(model, controller));
            else if (resource.equals("/todolist.html"))
              showView(new TasksView(model, controller));

            else if (resource.startsWith("/update")) {
              String name = resource.substring(7, resource.indexOf('.'));
              showView(new UpdateView(Integer.valueOf(name), model, controller));
            } else if (resource.startsWith("/task")) {
              String name = resource.substring(5, resource.indexOf('.'));
              showView(new TaskView(Integer.valueOf(name), model, controller));
            } else if (resource.startsWith("/issue")) {
              String name = resource.substring(6, resource.indexOf('.'));
              showView(new IssueView(Integer.valueOf(name), model, controller));
            } else if (resource.startsWith("/note")) {
              String name = resource.substring(5, resource.indexOf('.'));
              showView(new NoteView(Integer.valueOf(name), model, controller));
            } else if (resource.startsWith("/changelist")) {
              String name = resource.substring(11);
              showView(new TasksView(Integer.valueOf(name), model, controller));
            } else if (resource.startsWith("/changeproject")) {
              String name = resource.substring(14);
              showView(new IssuesView(Integer.valueOf(name), model, controller));
            } else if (resource.startsWith("/removetask")) {
              String name = resource.substring(11);
              int index = Integer.valueOf(name);
              int listID = model.getListIDByTaskID(index);
              model.removeTask(index);
              showView(new TasksView(listID, model, controller));
            } else if (resource.startsWith("/removenote")) {
              String name = resource.substring(11);
              int index = Integer.valueOf(name);
              model.removeNote(index);
              showView(new NotesView(model, controller));
            }


            break;
          } else if (c.startsWith("POST")) {
            ispost = true;
            String[] tokens = c.split(" ");
            postResource = tokens[1];
          } else if (c.startsWith("Content-Length")) {
            String[] x = c.split(":");
            contentL = Integer.valueOf(x[1].trim());
            System.out.println(contentL);
          }

        }
        if (ispost) {

          StringBuilder payload = new StringBuilder();
          while (in.ready()) {
            payload.append((char) in.read());
          }
          String coded = new String(payload);
          String result = URLDecoder.decode(coded);
          Map<String, String> postFieldsMap = new HashMap<String, String>();
          String postFields[] = result.trim().split("&");
          for (String postField : postFields) {
            try {
              String[] values = postField.split("=");
              String key = values[0].trim();
              String value = values[1].trim();
              System.out.println(key);
              System.out.println(value);


              postFieldsMap.put(key, value);
            } catch (Exception ex) {
              in.close();
              return;
            }
          }
          System.out.println(postFieldsMap);
          if (postResource.equals("/addproject")) {
            model.addProject(postFieldsMap.get("projectname"));
            showView(new IssuesView(model, controller));
          } else if (postResource.equals("/addlist")) {
            model.addList(postFieldsMap.get("listname"));
            showView(new TasksView(model, controller));
          } else if (postResource.equals("/addnote")) {
            String title = postFieldsMap.get("title");
            String text = postFieldsMap.get("note");
            model.addItem(new Note(title, text));
            showView(new NotesView(model, controller));
          } else if (postResource.startsWith("/addtask")) {
            int index = Integer.valueOf(postResource.substring(8));
            String text = postFieldsMap.get("task");
            model.addItemToList(new Task(text), index);
            showView(new TasksView(index, model, controller));
          } else if (postResource.startsWith("/addissue")) {
            int index = Integer.valueOf(postResource.substring(9));
            String text = postFieldsMap.get("issue");
            model.addItemToList(new Issue(text, false), index);
            showView(new IssuesView(index, model, controller));
          } else if (postResource.startsWith("/saveissue")) {
            int index = Integer.valueOf(postResource.substring(10));
            String text = postFieldsMap.get("note");
            String statusstr = postFieldsMap.get("status");
            Boolean status = false;
            if (statusstr.trim().equals("fixed")) status = true;
            model.updateItem(index, new Issue(text, status));
            int projectID = model.getProjectIDByIssueID(index);
            showView(new IssuesView(projectID, model, controller));
          } else if (postResource.startsWith("/savetask")) {
            int index = Integer.valueOf(postResource.substring(9));
            String text = postFieldsMap.get("task");
            model.updateItem(index, new Task(text));
            int listID = model.getListIDByTaskID(index);
            showView(new TasksView(listID, model, controller));
          } else if (postResource.startsWith("/savenote")) {
            int index = Integer.valueOf(postResource.substring(9));
            String text = postFieldsMap.get("note");
            String title = postFieldsMap.get("title");

            model.updateItem(index, new Note(title, text));

            showView(new NotesView(model, controller));
          }


        }
        in.close();


      } catch (IOException ex) {
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
