
   import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;




public class BoundStackTest {

       

public static final int MAX_GAMES = 100;


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
        
           
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea PlaylistTest\n");
        }

        System.out.println("=== BoundedStack Test Suite ===\n");

        testCreators();
        testpush();
        testpop();
        testObservers();
        testProducer();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    
    private static void testCreators() {

          
        System.out.println("-- Creators --");

        BoundedStack empty = new BoundedStack(100);
        check("new() -> size 0", empty.size() == 0);;
        check("new() -> contains nothing", empty.isEmpty());

        BoundedStack p = new BoundedStack(Arrays.asList("A", "B", "C"));
        check("new(list) -> size 3", p.size() == 3);
        check("new(list) -> contains B", p.peek("B"));
       
            

        // boundary: list ว่างคือขอบล่างที่ถูกต้อง
       BoundedStack fromEmpty = new BoundedStack(new ArrayList<String>());
        check("new(empty list) -> empty", fromEmpty.size() == 0);

        // input ที่ผิดเงื่อนไขต้องโยน exception ไม่ใช่ปล่อยผ่าน
        boolean threwDup = false;
        try {
            new BoundedStack(Arrays.asList("A", "A"));
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("new(duplicates) -> throws IllegalArgumentException", threwDup);

        boolean threwNull = false;
        try {
            new BoundedStack(Arrays.asList("A", null));
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("new(list with null) -> throws IllegalArgumentException", threwNull);

        boolean threwNullList = false;
        try {
            new BoundedStack(null);
        } catch (IllegalArgumentException e) {
            threwNullList = true;
        }
        check("new(null) -> throws IllegalArgumentException", threwNullList);
    }

    // --- Mutator: add ต้องรักษาลำดับและกันเพลงซ้ำ ---
    private static void testpush() {
        System.out.println("\n-- Add --");

        BoundedStack s = new BoundedStack(100);
        check("add(A) -> returns true", s.push("A"));
        check("add(A) -> size 1", s.size() == 1);
        check("add(A) -> found by contains", s.peek("A"));

        s.push("B");
        s.push("C");
        check("add preserves insertion order",
                s.games().equals(Arrays.asList("A", "B", "C")));

        // เพลงซ้ำไม่ใช่ error — คืน false เฉย ๆ
        check("add duplicate -> returns false", !s.push("A"));
        check("failed add leaves size unchanged", s.size() == 3);

        // input ที่ผิดเงื่อนไขต้องโยน exception
        boolean threwEmpty = false;
        try {
            s.push("");
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("add(empty string) -> throws IllegalArgumentException", threwEmpty);

        boolean threwNull = false;
        try {
            s.push(null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("add(null) -> throws IllegalArgumentException", threwNull);

        check("failed adds leave playlist unchanged", s.size() == 3);

        // boundary: เติมจนเต็มพอดีแล้วเติมเพิ่ม
        BoundedStack full = new BoundedStack(100);
        for (int i = 0; i < BoundedStack.MAX_GAMES; i++) {
            full.push("song" + i);
        }
        check("can fill up to MAX_GAMES", full.size() == BoundedStack.MAX_GAMES);
        check("add when full -> returns false", !full.push("one more"));
        check("full playlist stays at MAX_GAMES",
                full.size() == BoundedStack.MAX_GAMES);
    }

    // --- Mutator: remove ทั้งกรณีพบและไม่พบ ---
    private static void testpop() {
        System.out.println("\n-- Remove --");

        BoundedStack s = new BoundedStack(Arrays.asList("A", "B", "C"));
        check("remove(B) -> returns true", s.pop("B"));
        check("remove -> size decreases", s.size() == 2);
        check("remove -> song is gone", !s.peek("B"));
        check("remove keeps the others in order",
                s.games().equals(Arrays.asList("A", "C")));

        // ลบเพลงที่ไม่มีไม่ใช่ error — คืน false เฉย ๆ
        check("remove missing song -> returns false", !s.pop("nope"));
        check("failed remove leaves size unchanged", s.size() == 2);

        // boundary: ลบจนหมด
        s.pop("A");
        s.pop("C");
        check("remove all -> empty", s.size() == 0);
        check("remove on empty playlist -> returns false", !s.pop("A"));
    }

    // --- Observer ต้องไม่มี side effect ---
    private static void testObservers() {
        System.out.println("\n-- Observers --");

        BoundedStack s = new BoundedStack(Arrays.asList("A", "B"));
        check("size reports 2", s.size() == 2);
        check("contains finds an existing song", s.peek("A"));
        check("contains rejects a missing song", !s.peek("Z"));
        check("songs returns the full list in order",
                s.games().equals(Arrays.asList("A", "B")));

        int before = s.size();
        s.size();
        s.peek("A");
        s.games();
        check("observers have no side effects", s.size() == before);
    }

    // --- Producer ต้องคืนตัวใหม่ ไม่แก้ตัวเดิม ---
    private static void testProducer() {
        System.out.println("\n-- Producer (shuffled) --");

        BoundedStack original = new BoundedStack(Arrays.asList("A", "B", "C", "D"));
       BoundedStack shuffled = original.shuffled();

        check("shuffled has the same size", shuffled.size() == original.size());

        List<String> a = new ArrayList<String>(original.games());
        List<String> b = new ArrayList<String>(shuffled.games());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffled contains exactly the same songs", a.equals(b));

        check("shuffled does not mutate the original",
                original.games().equals(Arrays.asList("A", "B", "C", "D")));

        // mutate ตัวใหม่ต้องไม่กระทบตัวเดิม
        shuffled.push("E");
        check("mutating the result does not affect the original",
                original.size() == 4);

        // boundary: shuffle เพลย์ลิสต์ว่างต้องไม่พัง
        BoundedStack emptyShuffled = new BoundedStack(100).shuffled();
        check("shuffling an empty playlist is safe", emptyShuffled.size() == 0);
    }

   
    private static void testExposure() {
        System.out.println("\n-- Representation Exposure --");

        // ขาออก: แก้ list ที่ได้จาก songs() ต้องไม่กระทบ rep
       BoundedStack s = new BoundedStack(100);
        s.push("A");

        List<String> got = s.games();
        got.clear();
        check("clearing result of songs() does not affect playlist",
                s.size() == 1);

        got = s.games();
        got.add("injected");
        check("adding to result of songs() does not affect playlist",
                s.size() == 1 && !s.peek("injected"));

        // สองครั้งต้องเป็นคนละ object
        check("songs() returns a fresh list each call",
                s.games() != s.games());

        // ขาเข้า: แก้ list ที่ส่งให้ constructor ต้องไม่กระทบ rep
        List<String> input = new ArrayList<String>(Arrays.asList("A", "B"));
        BoundedStack p = new BoundedStack(input);

        input.clear();
        check("clearing constructor argument does not affect playlist",
                p.size() == 2);

        input.add("injected");
        check("adding to constructor argument does not affect playlist",
                !p.peek("injected"));
    }
}
