import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class BoundedStackTest {

       private static int passed = 0;
    private static int failed = 0;

   
    private static void check(String game, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + game);
        } else {
            failed++;
            System.out.println("[FAIL] " + game);
        }
    }

    public static void main(String[] args) {
        
           
        boolean assertson = false;
        assert assertson = true;
        if (!assertson) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea BoundedStackTest\n");
        }

        System.out.println("=== BoundedStack Test Suite ===\n");

        testCreators();
        testPush();
        testPop();
        testObservers();
        testProducer();
        testExposure();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    // ทดสอบ Constructor
    private static void testCreators() {
          
        System.out.println("-- Creators --");
        // สร้าง stack ว่าง
        BoundedStack empty = new BoundedStack(100);
        check("new() -> size 0", empty.size() == 0);;
        check("new() -> contains nothing", empty.isEmpty());

        // สร้างจาก List
       BoundedStack p = new BoundedStack(Arrays.asList("A", "B", "C"), 100);
       check("new(list) -> top is C", p.peek().equals("C"));
         check("new(list) -> preserves order",
               p.games().equals(Arrays.asList("A", "B", "C")));
       

        // ข้อมูลซ้ำ
        boolean threwDuplicate = false;
        try {
            new BoundedStack(Arrays.asList("A", "A"),100);
        } catch (IllegalArgumentException e) {
            threwDuplicate = true;
        }
        check("new(duplicates) -> throws IllegalArgumentException", threwDuplicate);

        // มีค่า null
        boolean threwNull = false;
        try {
            new BoundedStack(Arrays.asList("A", null), 100);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("new(list with null) -> throws IllegalArgumentException", threwNull);

        boolean threwNullList = false;
        try {
           new BoundedStack(null, 100);
        } catch (IllegalArgumentException e) {
            threwNullList = true;
        }
        check("new(null) -> throws IllegalArgumentException", threwNullList);
    }

    // ทดสอบการเพิ่มข้อมูลเข้า Stack
   private static void testPush() {
 
    // เพิ่มข้อมูลปกติ
    BoundedStack s = new BoundedStack(4);
    s.push("A");

    check("push A -> top is A", s.peek().equals("A"));

    // เพิ่ม String ว่าง
    boolean threwEmpty = false;
    try {
        new BoundedStack(4).push("");
    } catch (IllegalArgumentException e) {
        threwEmpty = true;
    }
    check("push empty string throws", threwEmpty);
    
    // เพิ่ม null
    boolean threwNull = false;
    try {
        new BoundedStack(3).push(null);
    } catch (IllegalArgumentException e) {
        threwNull = true;
    }
    check("push null throws", threwNull);

    // เพิ่มข้อมูลซ้ำ
    boolean threwDuplicate = false;
    try {
        BoundedStack b = new BoundedStack(List.of("A"), 3);
        b.push("A");
    } catch (IllegalStateException e) {
        threwDuplicate = true;
    }
    check("push duplicate throws", threwDuplicate);

    // เมื่อ Stack เต็ม
    BoundedStack full = new BoundedStack(2);

        full.push("A");
        full.push("B");

        check("stack is full", full.isFull());

        boolean threwFull = false;
            try {
    full.push("C");
            } catch (IllegalStateException e) {
    threwFull = true;
            }

        check("push when full throws", threwFull);
        // ถ้า capacity = 0
        BoundedStack zero = new BoundedStack(0);

        check("capacity 0 starts empty",
        zero.isEmpty());

        boolean threwZero = false;

        try {
    zero.push("A");
        } catch (IllegalStateException e) {
            threwZero = true;
        }   

        check("capacity 0 cannot push",
        threwZero);
        }

  // ทดสอบการนำข้อมูลออกจาก Stack
    private static void testPop() {

        //ตอนปกติ
    BoundedStack s = new BoundedStack(Arrays.asList("A", "B", "C"), 3);
    check("pop returns top game",
            s.pop().equals("C"));

    check("pop decreases size",
            s.size() == 2);

        //ตอน stack ว่าง
    boolean threwEmpty = false;

    try {
        BoundedStack empty = new BoundedStack(3);
        empty.pop();
    } catch (IllegalStateException e) {threwEmpty = true; }

    check("pop empty stack throws",  threwEmpty);
    
}
   // ทดสอบเมธอดที่ใช้ดูข้อมูล โดยไม่แก้ไข Stack
    private static void testObservers() {
        System.out.println("\n-- Observers --");

        // ตรวจสอบข้อมูล
       BoundedStack s = new BoundedStack(Arrays.asList("A", "B"), 100);
        check("size reports 2", s.size() == 2);
       check("peek returns top game", s.peek().equals("B"));
        check("games returns the full list in order",
                s.games().equals(Arrays.asList("A", "B")));

        // Observer ต้องไม่แก้ข้อมูลใน Stack
        int before = s.size();
        s.size();
        s.peek();
        s.games();
        check("observers have no side effects", s.size() == before);

        // ทดสอบ isEmpty
        check("empty stack isEmpty true",
        new BoundedStack(100).isEmpty());

        // ทดสอบ isFull
        BoundedStack full = new BoundedStack(2);
        full.push("A");
        full.push("B");

        check("full stack isFull true",
        full.isFull());
        check("not full stack isFull false",
        !new BoundedStack(100).isFull());
    }

    // ทดสอบ Producer ว่าสร้าง Stack ถูกมั้ย
    private static void testProducer() {
        System.out.println("\n-- Producer (copy) --");
        
        // copy ต้องมีข้อมูลเหมือนเดิม
        BoundedStack original = new BoundedStack(Arrays.asList("A", "B", "C", "D"),100);
      BoundedStack copy = original.copy();

        List<String> a = new ArrayList<String>(original.games());
        List<String> b = new ArrayList<String>(copy.games());
        check("copy has the same games", a.equals(b));

        // copy แล้วต้นฉบับไม่เปลี่ยน
        check("copy not mutate the original",
                original.games().equals(Arrays.asList("A", "B", "C", "D")));

       // แก้ไข copy ไม่กระทบต้นฉบับ
        copy.push("E");
        check("changing copy does not affect original",
        original.games().equals(Arrays.asList("A", "B", "C", "D")));

        // copy Stack ว่าง
        BoundedStack emptycopy= new BoundedStack(100).copy();
        check("copy empty stack is safe", emptycopy.size() == 0);
    }

   // ทดสอบการป้องกันการเข้าถึง representation โดยตรง
    private static void testExposure() {
        System.out.println("\n-- Representation Exposure --");

      // แก้ List จาก games ต้องไม่โดน Stack
       BoundedStack s = new BoundedStack(100);
        s.push("A");

        List<String> got = s.games();
        got.clear();
        check("clearing result of games() does not affect boundedstack",
                s.size() == 1);

        got = s.games();
        got.add("injected");
        check("adding to result of games() does not affect boundedstack",
               s.size() == 1 && !s.games().contains("injected"));
     
               // games() ต้องคืน List ใหม่ทุกครั้ง
        check("games() returns a fresh list each call",
                s.games() != s.games());
    
                // แก้ List ที่ส่งเข้า Constructor ต้องไม่โดน Stack
        List<String> input = new ArrayList<String>(Arrays.asList("A", "B"));
        BoundedStack p = new BoundedStack(input,100);

        input.clear();
        check("clearing constructor argument does not affect boundedstack",
                p.size() == 2);

        input.add("injected");
        check("adding to constructor argument does not affect boundedstack",
               !p.games().contains("injected"));
    }
}