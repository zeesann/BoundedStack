import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * BoundedStack คือ กล่องเก็บชื่อเกมตามลำดับ
 */
public class BoundedStack {

    public static final int MAX_GAMES = 100;

    private final List<String> games;
    private final int capacity;

    //AF(games, capacity) = ลำดับจากบนลงล่าง (games.get(size-1)) , (games.get(size-2)) , ... , (games.get(size-100)) 
    // โดยตัวแรกของลำดับคือ...ของ Strack และตัวสุดท้าย (games.get(size-100)) คือ ... ของ Strack capacity คือ ...
    //RI
    // -game ไม่เป็น null
    // -ไม่มีสมาชิกเป็น null
    // -game ไม่มีสตริงว่าง
    // -ชื่อเกมไม่ซ้ำกัน
    // -ไม่เกิน capacity (100) เกม

    private void checkRep() {

        assert games != null : "game not null";
        assert games.size() <= capacity;
        assert capacity >= 0 && capacity <= MAX_GAMES;

        Set<String> seen = new HashSet<>();
        for (String s : games) {
            assert s != null;
            assert !(s.isEmpty());
            assert seen.add(s) : "ชื่อเกมซ้ำ: " + s;
        }
    }
    
    /**
     * 
     * @param capacity จำนวนที่ชื่อเกมที่เก็บได้ (MAX_GAMES = 100)
     */
    public BoundedStack(int capacity){
        if (capacity < 0 || capacity > MAX_GAMES) throw new IllegalArgumentException();
        this.games = new ArrayList<>();
        this.capacity = capacity;
    }


    /**
     * 
     * @param initial รายชื่อเกมเริ่มต้น ต้องไม่ซ้ำและไม่เกิน MAX_GAMES
     * @throws IllegalArumentException ถ้า initial ผิดเงื่อนไข
     */
    public BoundedStack(List<String> initial , int capacity) {

        if(initial==null) throw new IllegalArgumentException();
        if(initial.size()>MAX_GAMES) throw new IllegalArgumentException();
        Set<String> seen = new HashSet<>();
        for(String s : initial){
            if(s==null || s.isEmpty()) throw new IllegalArgumentException();
            if(!seen.add(s)) throw new IllegalArgumentException();
    }
        this.games = new ArrayList<>(initial); 
        if (capacity < initial.size() || capacity > MAX_GAMES) throw new IllegalArgumentException();
    this.capacity = capacity;  
        checkRep();

    }
    
    /**
     * 
     * @param game ชื่อเกม ต้องไม่เป็น null และไม่เป็นสตริงว่าง  
     * @throws IIlegalArguments ถ้า game เป็น null หรือสตริงว่าง , llegalStateException ถ้า games = capacity
     */
    public void push(String game){
        if (game == null || game.isEmpty()) throw new IllegalArgumentException(); 
        if (games.contains(game) || games.size() == capacity) throw new IllegalStateException(); 
        games.add(game);
        checkRep();
    }

    /**
     * 
     * @return ลบตัวบนสุด และคืนค่าตัวบนสุดอันใหม่     
     * @throws Illegalexeption ถ้า games ว่าง
     */
    public String pop(){
        if (games.isEmpty()) throw new IllegalStateException();
    String top = games.remove(games.size() - 1);
    checkRep();
    return top;
        
    }

    /**
     * 
     * @return คืนจำนวนเกมใน BoundedStrack
     */
    public int size(){
        return games.size();
    }

    /**
     * ดูเกมที่อยู่บนสุดของ stack โดยไม่ลบออก
     * @return ชื่อเกมที่อยู่บนสุด
     * @throws IllegalStateException ถ้า stack ว่าง
     * 
     */
    public String peek(){
          if (games.isEmpty()) throw new IllegalStateException("stack ว่าง ไม่สามารถ peek ได้");
    return games.get(games.size() - 1);
    }

    public List<String> games() {
    return new ArrayList<>(games);
}



    /**
     * 
     * @return 
     */
    public BoundedStack copy() {
    BoundedStack result = new BoundedStack(this.capacity);

    for (String game : games) {
        result.push(game);
    }

    return result;
}

    /**
     * 
     * @return คืนค่าเกมที่ว่าง
     */
    public boolean isEmpty(){
        return games.isEmpty();
    }

    /**
     * 
     * @return คืนค่าเกมที่เท่ากับ capacity
     */
    public boolean isFull() {
        return games.size() == capacity;
    }

}