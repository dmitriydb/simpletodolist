package simpletodolist.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import simpletodolist.Issue;
import simpletodolist.Note;
import simpletodolist.Task;
import simpletodolist.controller.ToDoController;
import simpletodolist.model.ToDoModel;
import simpletodolist.webserver.ToDoSimpleWebServer;

/**
 * @author Dmitriy D
 */
public class NoteView extends ToDoHTMLView {

  private int editingIndex;

  public NoteView(int index, ToDoModel model, ToDoController controller) {
    super(model, controller);
    editingIndex = index;
  }

  public int getIndex() {
    return editingIndex;
  }

  public synchronized void createViewFromModel() {
    try {
      Note note = model.getNoteByID(editingIndex);
      page = new ArrayList<String>();
      BufferedReader in = new BufferedReader(new InputStreamReader(NoteView.class.getClassLoader().getResourceAsStream("html/note1.html")));

      String line;
      while ((line = in.readLine()) != null) {
        if (line.contains("#num")) {
          line = line.replace("#num", String.valueOf(editingIndex));
          page.add(line);
        } else if (line.contains("#title")) {
          line = line.replace("#title", note.getTitle());
          page.add(line);
        } else if (line.contains("#text")) {
          line = line.replace("#text", note.getText());
          page.add(line);
        } else if (line.contains("#entry")) {
          String[] lines = note.toHTML();
          for (String x : lines)
            page.add(x);
        } else
          page.add(line);
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void update() {
    createViewFromModel();
  }

}
