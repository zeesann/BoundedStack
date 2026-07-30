import java.util.ArrayList;
import java.util.Collections;
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

    //AF(games, capacity)
    //RI
    // -game ไม่เป็น null
    // -ไม่มีสมาชิกเป็น null
    // -game ไม่มีสตริงว่าง
    // -ชื่อเกมไม่ซ้ำกัน
    // -ไม่เกิน MAX_GAMES (100) เกม

    private void checkRep() {

        assert games != null;
        assert games.size() <= MAX_GAMES;
        assert games.size() <= capacity;
        assert capacity >= 0 && capacity <= MAX_GAMES;

        Set<String> seen = new HashSet<>();
        for (String s : games) {
            assert s != null;
            assert !s.isEmpty();
            assert seen.add(s) : "ชื่อเกมซ้ำ: " + s;
        }
    }

    /**
     * 
     * @param initial รายชื่อเกมเริ่มต้น ต้องไม่ซ้ำและไม่เกิน MAX_GAMES
     * @throws IllegalArumentException ถ้า initial ผิดเงื่อนไข
     */
    public BoundedStack(List<String> initial) {

        if(initial==null) throw new IllegalArgumentException();
        if(initial.size()>MAX_GAMES) throw new IllegalArgumentException();
        Set<String> seen = new HashSet<>();
        for(String s : initial){
            if(s==null || s=="") throw new IllegalArgumentException();
            if(!seen.add(s)) throw new IllegalArgumentException();
    }
        this.games = new ArrayList<>(initial); 
        this.capacity = initial.size();  
        checkRep();

    }
    /**
     * 
     * @param capacity จำนวนที่ชื่อเกมที่เก็บได้ (MAX_GAMES = 100)
     */
    public BoundedStack(int capacity){
        this.games = new ArrayList<>();
        this.capacity = capacity;
    }

    /**
     * 
     * @param game ชื่อเกม ต้องไม่เป็น null และไม่เป็นสตริงว่าง
     * @return true ถ้าเพิ่มสำเร็จ , flase ถ้ามีเพลงอยู่แล้วหรือเต็มแล้ว
     * @throws IIlegaArumentException ถ้า game เป็น null หรือสตริงว่าง
     */
    public boolean push(String game){
        if (game == null || game==" ") throw new IllegalArgumentException();
        if (games.contains(game) || games.size() == capacity)
            return false;
        games.add(game);
        checkRep();
        return true;
    }

    /**
     * 
     * @param game ชื่อเกมที่ต้องการลบ
     * @return true ถ้าลบสำเร็จ , flase ถ้าไม่พบเกมนี้
     */
    public boolean pop(String game){
        if (!games.contains(game)) return false;
        games.remove(game);
        checkRep();
        return true;
    }

    /**
     * 
     * @return คืนจำนวนเกมใน BoundedStrack
     */
    public int size(){
        return games.size();
    }

    /**
     * 
     * @param game ตรวจว่ามีเกมนี้อยู่หรือไม่
     * @return คืนค่าเกมที่มี
     */
    public boolean peek(String game){
        return games.contains(game);
    }

    /**
     * 
     * @return boundedStrack ที่สลับลำดับแล้ว
     */
    public BoundedStack shuffled(){
        List<String> copy = new ArrayList<>(games);
        Collections.shuffle(copy);
        BoundedStack result = new BoundedStack(this.capacity);
        for (String g : copy) result.push(g);
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