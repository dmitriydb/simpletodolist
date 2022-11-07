package simpletodolist;

/**
 * @author Dmitriy D
 */
public class Note extends Item {

  private String title;

  public Note(String title, String text) {
    super(text);
    this.title = title;
  }

  public Note(int id, String title, String text, String time) {
    super(id, text, time);
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public String[] toHTML() {
    return new String[]{
        "<article class=\"noteItem\">",
        "<p><b>" + title + "</b><br>" + getText() + "<br><br>" +
            "<sub>" + timeStamp + "</sub></p>",
        "</article>"};
  }

  public String[] toFullHTML() {
    return new String[]{
        "<article class=\"noteItem\">",
        "<a href=\"note" + id + ".html\"><p><b>" + title + "</b><br>" + getText() + "<br><br>",
        "<sub>" + timeStamp + "</sub></p></a>",
        "<div class=\"remove\"><a href=\"removenote" + id + "\">remove</a></div>",
        "</article>"
    };
  }


  public String[] toUpdate() {
    return new String[]{
        "<article class=\"updateItem\">",
        "<a href=\"note" + id + ".html\"><p>New note added: " + title + "<br><br>",
        "<sub>" + timeStamp + "<sub></p></a>",
        "</article>"};


  }
}              
    
    
