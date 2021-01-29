
package simpletodolist.model;

import simpletodolist.Task;
import java.util.*;
import simpletodolist.Item;
import simpletodolist.ModelChangeListener;

/**
 *
 * @author Dmitriy D
 */
public class ToDoSimpleModel implements ToDoModel{
    private LinkedList<ModelChangeListener> modelListeners;
    private ArrayList<Item> itemList;
    
    public ToDoSimpleModel(){
        itemList = new ArrayList<Item>();
        modelListeners = new LinkedList<ModelChangeListener>();
    }
    
    public void addListener (ModelChangeListener listener){
        modelListeners.add(listener);
    }
    
    private void notifyAllListeners(){
        for (ModelChangeListener l : modelListeners){
            l.update();
        }
    } 
    
    public void addItem(Item t){
        itemList.add(t);
        notifyAllListeners();
    }
    
    public void removeItem (Item t){
        itemList.remove(t);
        notifyAllListeners();
    }

    public void removeItem (int index){
        itemList.remove(itemList.get(index));
        notifyAllListeners();
    }

    
    public void updateItem (int index, Item newItem){
        itemList.set(index, newItem);
        notifyAllListeners();
    }
    
    public Item[] getItems(){
        Item[] array = new Item[itemList.size()];
        itemList.toArray(array);
        return array;
    }
    
}
