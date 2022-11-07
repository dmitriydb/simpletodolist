package simpletodolist;

/**
 * @author Dmitriy D
 */
public class TodoList extends Item {


  public TodoList(String text) {
    super(text);
  }

  public TodoList(int id, String text) {
    super(id, text);
  }


  public String[] toHTML() {

    return new String[]{
        "<article class=list>",
        "<a href=\"/changelist" + id + "\"><p>" + text + "</p></a>",
        " </article>"};
  }


  public String[] toFullHTML() {
    return new String[]{
    };
  }

  public String[] toUpdate() {
    return new String[]{};


  }

}
