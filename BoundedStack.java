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

    //AF(games, capacity) =  games แทนลำดับข้อมูลใน Stack โดย games.get(games.size()-1) คือข้อมูลบนสุดของ Stack
    // games.get(0) คือข้อมูลที่ถูกเพิ่มก่อนสุด , capacity คือจำนวนข้อมูลสูงสุดที่ Stack สามารถเก็บได้


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
    * เพิ่มชื่อเกมเข้าไปบนสุดของ Stack
    *
    * @param game ชื่อเกมที่ต้องการเพิ่ม ต้องไม่เป็น null และไม่เป็นค่าว่าง
    * @throws IllegalArgumentException ถ้า game ไม่ถูกต้อง
    * @throws IllegalStateException ถ้า Stack เต็มหรือมีชื่อเกมซ้ำ
     */
    public void push(String game){
        if (game == null || game.isEmpty()) throw new IllegalArgumentException(); 
        if (games.contains(game) || games.size() == capacity) throw new IllegalStateException(); 
        games.add(game);
        checkRep();
    }

    /**
        * ลบและคืนค่าชื่อเกมที่อยู่บนสุดของ Stack
    *
    * @return ชื่อเกมที่ถูกนำออก
    * @throws IllegalStateException ถ้า Stack ว่าง
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
    * ดูชื่อเกมที่อยู่บนสุดโดยไม่ลบออก
    *
     * @return ชื่อเกมบนสุดของ Stack
    * @throws IllegalStateException ถ้า Stack ว่าง
    */
    public String peek(){
          if (games.isEmpty()) throw new IllegalStateException("stack ว่าง ไม่สามารถ peek ได้");
    return games.get(games.size() - 1);
    }

    /**
     * 
     * @return
     */
    public List<String> games() {
    return new ArrayList<>(games);
}


      /**
    * สร้างสำเนาใหม่ของ Stack ที่มีข้อมูลเหมือนเดิม
    *
    * @return BoundedStack ตัวใหม่
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