import java.util.ArrayList;
import java.util.List;

/**
 * 
 * BoundedStack คือ...
 */
public class BoundedStack {
    
    private final List<String> elements;
    private final int capacity;

    //AF(elements, capacity)
    //RI
    // -
    // -

    /**
     * 
     * @param capacity
     */
    public BoundedStack(int capacity){
        this.elements = new ArrayList<>();
        this.capacity = capacity;
    }

    /**
     * 
     * @param s
     */
    public void push(String s){

    }
}