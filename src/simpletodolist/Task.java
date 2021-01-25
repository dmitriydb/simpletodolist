
package simpletodolist;

/**
 *
 * @author Dmitriy D
 */
public class Task {
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public Task (String description){
        this.description = description;
    }
}
