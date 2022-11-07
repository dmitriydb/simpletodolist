package simpletodolist;

/**
 * @author Dmitriy D
 */
public abstract class Item {
  protected String text = "nothing";
  protected String timeStamp = null;
  protected int id = -1;

  public Item(int id, String text) {
    this.id = id;
    this.text = text;

  }


  public Item(int id, String text, String timeStamp) {
    this.id = id;
    this.text = text;
    this.timeStamp = timeStamp;
  }

  public Item(String text) {
    this.text = text;

  }

  public int getID() {
    return id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;

  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public abstract String[] toUpdate();

  public abstract String[] toFullHTML();

  public abstract String[] toHTML();
}
