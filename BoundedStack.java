import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//จัดทำโดย
//นางสาว บัณฑิตา ทองสนธิ 6821601151
//นางสาว พนัชกร สนกลัด 6821601216


/**
 * 
 * BoundedStack คือ กล่องเก็บชื่อเกมแบบ stack ที่มีความจุจำกัด
 * เกมที่ push เข้าล่าสุดจะถูก pop ออกก่อนเสมอ และห้ามมีชื่อเกมซ้ำกันใน stack เดียวกัน
 */
public class BoundedStack {

    // ===== representation =====

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

    // Safety from rep exposure:
    //   stack ใหม่ที่คืนออกไปไม่ได้ใช้ list ร่วมกับ stack เดิม
    //   ต่อให้เอา stack ใหม่ไป push/pop ต่อ ก็ไม่กระทบ stack ต้นฉบับ

        // ===== Checkrep =====

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
    

        // ===== Creator =====

    /**
        * สร้างแสตกว่างเปล่า
     * @param capacity จำนวนชื่อเกมสูงสุดที่เก็บได้ต้องมากกว่า 0
     * @throws ความจุของ capacity < 0 หรือ capacity > MAX_GAMES
     */
    public BoundedStack(int capacity){
        if (capacity < 0 || capacity > MAX_GAMES) throw new IllegalArgumentException();
        this.games = new ArrayList<>();
        this.capacity = capacity;
        checkRep();
    }

    /**
        * สร้างแสตกจาก list ที่มีอยู่แล้ว
     * @param game  ต้องไม่ซ้ำและไม่เกิน MAX_GAMES --capacity ต้อง >0 namebook ต้อง != null และ size <= capacity
     * @throws IllegalArumentException ถ้า game เป็น null
     * @throws IllegalArumentException ถ้าชื่อเกมที่เก็บอยู่ใน game เกิน MAX_GAMES
     * @throws IllegalArumentException ถ้าชื่อ เป็น null หรือ สตริงว่าง
     * @throws IllegalArumentException ถ้าเจอชื่อซ้ำ
     * @throws IllegalArumentException ถ้า capacity น้อยกว่าชื่อเกมที่เก็บอยู่ใน game และเกิน MAX_GAMES
     */
    public BoundedStack(List<String> game , int capacity) {

        if(game==null) throw new IllegalArgumentException();
        if(game.size()>MAX_GAMES) throw new IllegalArgumentException();
        Set<String> seen = new HashSet<>();
        for(String s : game){
            if(s==null || s.isEmpty()) throw new IllegalArgumentException();
            if(!seen.add(s)) throw new IllegalArgumentException();
    }
        this.games = new ArrayList<>(game); 
        if (capacity < game.size() || capacity > MAX_GAMES) throw new IllegalArgumentException();
    this.capacity = capacity;  
        checkRep();

    }
    

        //  ===== Mutators =====

    /**
        * เพิ่มชื่อเกมเข้าไปบนสุดของ Stack
    * @param game ชื่อเกมที่ต้องการเพิ่ม ต้องไม่เป็น null และไม่เป็นค่าว่าง
    * @throws IllegalArgumentException ถ้า game เป็น null หรือ สตริงว่าง
    * @throws IllegalStateException ถ้า Stack เต็มหรือมีชื่อเกมซ้ำ
     */
    public void push(String game){
        if(game == null || game.isEmpty()) throw new IllegalArgumentException(); 
        if(games.contains(game) || games.size() == capacity) throw new IllegalStateException(); 
        games.add(game);
        checkRep();
    }

    /**
        * ลบและคืนค่าชื่อเกมที่อยู่บนสุดของ Stack
    * @return ชื่อเกมที่ถูกนำออก
    * @throws IllegalStateException ถ้า Stack ว่าง
    */
    public String pop(){

        if (games.isEmpty()) throw new IllegalStateException();
            String top = games.remove(games.size() - 1);
        checkRep();
        return top;
        
    }

        // ===== Observers ===== 
    
    /**
        * ดูชื่อเกมที่อยู่บนสุดโดยไม่ลบออก
    * @return ชื่อเกมบนสุดของ Stack
    * @throws IllegalStateException ถ้า Stack ว่าง
    */
    public String peek(){
          if (games.isEmpty()) throw new IllegalStateException("stack ว่าง ไม่สามารถ peek ได้");
    return games.get(games.size() - 1);
    }


    /**
        *คืนจำนวนเกมที่อยู่ใน stack ขณะนี้
    * @return จำนวนเกมใน BoundedStack
     */
    public int size(){
        return games.size();
    }

   /**
        * คืนรายชื่อเกมทั้งหมดใน stack เรียงจากล่างสุดไปบนสุด (สำเนา ไม่กระทบ state จริง)
    * @return list ใหม่ของชื่อเกมตามลำดับ
    */
    public List<String> games() {
    return new ArrayList<>(games);
    }

    /**
        * ตรวจว่า stack ไม่มีเกมอยู่เลยหรือไม่
    * @return true ถ้า stack ว่าง (ไม่มีเกมอยู่เลย), false ถ้ามีอย่างน้อย 1 เกม
     */
    public boolean isEmpty(){
        return games.isEmpty();
    }

    /**
        * ตรวจว่า stack เก็บเกมครบตาม capacity แล้วหรือไม่
    * @return true ถ้าจำนวนเกมใน stack เท่ากับ capacity (push เพิ่มไม่ได้แล้ว), false ถ้ายังไม่เต็ม
     */
    public boolean isFull() {
        return games.size() == capacity;
    }


        //  ===== Producer =====

    /**
        * สร้างสำเนาใหม่ของ Stack ที่มีข้อมูลเหมือนเดิม
    * @return BoundedStack ตัวใหม่
    */
    public BoundedStack copy() {
    BoundedStack result = new BoundedStack(this.capacity);

    for (String game : games) {
        result.push(game);
    }
    return result;
    }

}