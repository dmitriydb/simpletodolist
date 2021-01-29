
package simpletodolist;

/**
 *
 * @author Dmitriy D
 */
public class Task extends Item{
    
    
    public Task(String text){
        super(text);
    }
    
    
    public String[] toHTML(){
     return new String[]{
     "<article class=task>",
     "<p>Task #" + id +": " + getText() +"</p>",
     "</article>"};
    }
    
    public String[] toFullHTML(){
     return new String[]{
      "<article class=task>",
        "<a href=\"task"+id+".html\"><p>Task#"+id+":"+ text+"</p></a>",
       "<div class=\"remove\"><a href=\"removetask\""+id+">remove</a></p></div>",
             
     "</article>"
  };
    }
    
    
    public String[] toUpdate(){
          return new String[]{
              "<article class=\"updateItem\">",
   "<a href=\"update"+id+".html\"><p>Task#"+id+" added: " +text+ "<br><br>",
    "<sub>"+timeStamp+"<sub></p></a>",
  "</article>"};
    
 
    }
}
    
    
    
