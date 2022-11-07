package simpletodolist;

/**
 * @author Dmitriy D
 */
public class Issue extends Item {

  private boolean status = false;

  public Issue(String text, boolean status) {
    super(text);
    this.status = status;
  }

  public Issue(int id, String text, String time, boolean status) {
    super(id, text, time);
    this.status = status;
  }

  public boolean getStatus() {
    return status;
  }


  public String[] toHTML() {
    String st = status ? "fixed" : "not fixed";
    return new String[]{
        "<article class=issue>",
        "<p>" + getText() + "<br> Status:<strong>" + st + "</strong></p>",
        "</article>"
    };

  }

  public String[] toFullHTML() {
    String st = status ? "<span style=\"color:green\"><strong>fixed</strong></span>" : "<span style=\"color:red\"><strong>not fixed</strong></span>";
    return new String[]{
        "<article class=issue>",
        "<a href=\"issue" + id + ".html\"><p>" + getText() + "<br> Status: " + st + "</p></a>",
        "</article>"
    };
  }

  public String[] toUpdate() {
    return new String[]{
        "<article class=\"updateItem\">",
        "<a href=\"issue" + id + ".html\"><p>New issue added: " + text + "<br><br>",
        "<sub>" + timeStamp + "<sub></p></a>",
        "</article>"};
  }

}
