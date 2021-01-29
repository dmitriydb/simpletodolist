
package simpletodolist;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Dmitriy D
 */
public abstract class Item {
    protected String text;
    protected String timeStamp;
    
    public void setText(String text) {
        this.text = text;
       
    }

    protected void updateTimeStamp(){
        timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    }
    public String getText() {
        return text;
    }
    
    public Item (String text){
        this.text = text;
         updateTimeStamp();
    }
    
    
    public abstract String[] toUpdate(int id);
    public abstract String[] toFullHTML(int id);
    public abstract String[] toHTML(int id);
}
