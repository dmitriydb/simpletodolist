package simpletodolist.model;

import simpletodolist.Issue;
import simpletodolist.Item;
import simpletodolist.Note;
import simpletodolist.Project;
import simpletodolist.Task;
import simpletodolist.TodoList;

/**
 * @author Dmitriy D
 */
public interface ToDoModel {
  public void addItem(Item t);

  public void addItemToList(Item t, int listID);

  public void removeTask(int id);

  public void removeNote(int id);

  public void updateItem(int index, Item newItem);

  public Item[] getItems();

  public void addProject(String name);

  public void addList(String name);

  public Project[] getProjects();

  public TodoList[] getLists();

  public int getFirstListID();

  public int getFirstProjectID();

  public Task[] getTasksFromList(int id);

  public Issue[] getIssuesFromProject(int id);

  public Issue getIssueByID(int id);

  public Task getTaskByID(int id);

  public Note getNoteByID(int id);

  public int getProjectIDByIssueID(int id);

  public int getListIDByTaskID(int id);

}
