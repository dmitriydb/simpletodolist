
package simpletodolist;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Dmitriy D
 */
public abstract class Item {
    protected String text = "nothing";
    protected String timeStamp = "not set";
    protected int id = -1;
    
    public void setText(String text) {
        this.text = text;
       
    }

    protected void updateTimeStamp(){
        timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    }
    public String getText() {
        return text;
    }
    
    public Item (int id, String text){
       this.id = id;
        this.text = text;
         updateTimeStamp();
    }
  
      public Item (String text){
       this.id = id;
        this.text = text;
         updateTimeStamp();
    }
    
    
    public abstract String[] toUpdate();
    public abstract String[] toFullHTML();
    public abstract String[] toHTML();
}
