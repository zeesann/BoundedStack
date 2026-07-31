import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class BoundStackTest {

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
                    + " - re-run with: java -ea BoundedStrackTest\n");
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

    
    private static void testCreators() {

          
        System.out.println("-- Creators --");

        BoundedStack empty = new BoundedStack(100);
        check("new() -> size 0", empty.size() == 0);;
        check("new() -> contains nothing", empty.isEmpty());

       BoundedStack p = new BoundedStack(Arrays.asList("A", "B", "C"), 100);
        check("new(list) -> size 3", p.size() == 3);
       check("new(list) -> top is C", p.peek().equals("C"));
         check("new(list) -> preserves order",
               p.games().equals(Arrays.asList("A", "B", "C")));
       
            

       
       BoundedStack fromEmpty = new BoundedStack(new ArrayList<String>(),100);
        check("new(empty list) -> empty", fromEmpty.size() == 0);

      
        boolean threwup = false;
        try {
            new BoundedStack(Arrays.asList("A", "A"),100);
        } catch (IllegalArgumentException e) {
            threwup = true;
        }
        check("new(duplicates) -> throws IllegalArgumentException", threwup);

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

   private static void testPush() {
    System.out.println("\n-- Push --");

 
    BoundedStack s = new BoundedStack(4);
    s.push("A");

    check("push A -> top is A", s.peek().equals("A"));
    check("push A -> size is 1", s.size() == 1);

    boolean threwEmpty = false;
    try {
        new BoundedStack(4).push("");
    } catch (IllegalArgumentException e) {
        threwEmpty = true;
    }
    check("push empty string throws", threwEmpty);
    
    boolean threwNull = false;
    try {
        new BoundedStack(3).push(null);
    } catch (IllegalArgumentException e) {
        threwNull = true;
    }
    check("push null throws", threwNull);

    boolean threwup = false;
    try {
        BoundedStack b = new BoundedStack(List.of("A"), 3);
        b.push("A");
    } catch (IllegalStateException e) {
        threwup = true;
    }
    check("push duplicate throws", threwup);

    boolean threwFull = false;
    try {
        BoundedStack full = new BoundedStack(List.of("A", "B"), 2);
        full.push("C");
    } catch (IllegalStateException e) {
        threwFull = true;
    }
    check("push when full throws", threwFull);
}

  
    private static void testPop() {

    BoundedStack s = new BoundedStack(Arrays.asList("A", "B", "C"), 3);

    check("pop returns top game",
            s.pop().equals("C"));

    check("pop decreases size",
            s.size() == 2);

    boolean threwEmpty = false;

    try {
        BoundedStack empty = new BoundedStack(3);
        empty.pop();
    } catch (IllegalStateException e) {threwEmpty = true; }

    check("pop empty stack throws",  threwEmpty);

    BoundedStack order = new BoundedStack(3);

    order.push("A");
    order.push("B");

    check("pop follows LIFO",
            order.pop().equals("B"));
}
   
    private static void testObservers() {
        System.out.println("\n-- Observers --");

       BoundedStack s = new BoundedStack(Arrays.asList("A", "B"), 100);
        check("size reports 2", s.size() == 2);
       check("peek returns top game", s.peek().equals("B"));
        check("games returns the full list in order",
                s.games().equals(Arrays.asList("A", "B")));

        int before = s.size();
        s.size();
        s.peek();
        s.games();
        check("observers have no side effects", s.size() == before);
    }


    private static void testProducer() {
        System.out.println("\n-- Producer (copy) --");

        BoundedStack original = new BoundedStack(Arrays.asList("A", "B", "C", "D"),100);
      BoundedStack copy = original.copy();

        check("copy has the same size", copy.size() == original.size());

        List<String> a = new ArrayList<String>(original.games());
        List<String> b = new ArrayList<String>(copy.games());
        check("copy exactly the same game", a.equals(b));

        check("copy not mutate the original",
                original.games().equals(Arrays.asList("A", "B", "C", "D")));

       
        copy.push("E");
        check("changing copy does not affect original",
                original.size() == 4);

       
        BoundedStack emptycopy= new BoundedStack(100).copy();
        check("copy empty stack is safe", emptycopy.size() == 0);
    }

   
    private static void testExposure() {
        System.out.println("\n-- Representation Exposure --");

      
       BoundedStack s = new BoundedStack(100);
        s.push("A");

        List<String> got = s.games();
        got.clear();
        check("clearing result of games() does not affect boundedstrack",
                s.size() == 1);

        got = s.games();
        got.add("injected");
        check("adding to result of games() does not affect boundedstrack",
               s.size() == 1 && !s.games().contains("injected"));
     
        check("games() returns a fresh list each call",
                s.games() != s.games());
    
        List<String> input = new ArrayList<String>(Arrays.asList("A", "B"));
        BoundedStack p = new BoundedStack(input,100);

        input.clear();
        check("clearing constructor argument does not affect boundedstrack",
                p.size() == 2);

        input.add("injected");
        check("adding to constructor argument does not affect boundedstrack",
               !p.games().contains("injected"));
    }
}
    
