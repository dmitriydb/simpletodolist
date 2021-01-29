
package simpletodolist;

/**
 *
 * @author Dmitriy D
 */
public class Note extends Item{
    
    private String title;
    public Note(String title, String text){
        super(text);
        this.title = title;
    }
    
    
    public String[] toHTML(int id){
     return new String[]{
    "<article class=\"noteItem\">",
  "<p><b>" + title +"</b><br>" + getText() +"<br><br>",
  "<sub>22 января 2021<sub></p>",
"</article>"};
}          
    
  public String[] toFullHTML(int id){
     return new String[]{
   "<article class=\"noteItem\">",
    "<a href=\"note"+id+".html\"><p><b>" + title +"</b><br>"+getText()+"<br><br>",
    "<sub>"+timeStamp+"<sub></p></a>",
    "<div class=\"remove\"><a href=\"removenote"+id+"\">remove</a></div>",         
 "</article>"  
     };
  }
  
  
  public String[] toUpdate(int id){
          return new String[]{
              "<article class=\"updateItem\">",
   "<a href=\"update"+id+".html\"><p>New note added: " +title+ "<br><br>",
    "<sub>"+timeStamp+"<sub></p></a>",
  "</article>"};
    
 
    }
}              
    
    
