import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * BoundedStack คือ ไว้เก็บชื่อเกมโปรดตามลำดับ
 */
public class BoundedStack {

    public static final int MAX_GAMES = 100;

    private final List<String> elements;
    private final int capacity;

    //AF(elements, capacity)
    //RI
    // -game ไม่เป็น null
    // -ไม่มีสมาชิกเป็น null
    // -game ไม่มีสตริงว่าง
    // -ชื่อเกมไม่ซ้ำกัน
    // -ไม่เกิน MAX_GAMES (100) เกม

    
    private void checkRep() {
        
    assert elements != null;
    assert elements.size() <= MAX_GAMES;
    assert elements.size() <= capacity;
    assert capacity >= 0 && capacity <= MAX_GAMES;

    Set<String> seen = new HashSet<>();
    for (String s : elements) {
        assert s != null;
        assert !s.isEmpty();
        assert seen.add(s) : "ชื่อเกมซ้ำ: " + s;
    }
}
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
     * @param game
     * @return
     */
    public boolean  push(String game){
        if(game==null || game=="") throw new IllegalArgumentException();
        if (elements.contains(game) || elements.size() == capacity)
            return false;
        elements.add(game);
        checkRep();
        return true;

    }

    /**
     * 
     * @param game
     * @return
     */
    public boolean pop(String game){
        if(!elements.contains(game)) return false;
        elements.remove(game);
        checkRep();
        return true;

    }

    /**
     * 
     * @return
     */
    public int size(){

        return elements.size();

    }

    /**
     * 
     * @param game
     * @return
     */
    public boolean peek(String game){

        return elements.contains(game);

    }


    /**
     * 
     * @return
     */
    public BoundedStack shuffled(){
        List<String> copy = new ArrayList<>(elements);
       Collections.shuffle(copy);
        BoundedStack result = new BoundedStack(this.capacity);
    for (String g : copy) result.push(g);
    return result;
    }
    
    /**
     * 
     * @param s
     * @return
     */
    public boolean  isEmpty(String s){
        return elements.isEmpty();
    }

    /**
     * 
     * @return
     */
    public boolean isFull() {
    return elements.size() == capacity;
}
     
}
