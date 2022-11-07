package simpletodolist;

/**
 * @author Dmitriy D
 */
public class Project extends Item {


  public Project(String text) {
    super(text);
  }

  public Project(int id, String text) {
    super(id, text);
  }

  public String[] toHTML() {

    return new String[]{
        "<article class=project>",
        "<a href=\"/changeproject" + id + "\"><p>" + text + "</p></a>",
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
