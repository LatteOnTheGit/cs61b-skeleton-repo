package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing expect_Alist = new AListNoResizing<Integer>();
        BuggyAList test_Alist = new BuggyAList<Integer>();
        expect_Alist.addLast(4);
        test_Alist.addLast(4);
        expect_Alist.addLast(5);
        test_Alist.addLast(5);
        expect_Alist.addLast(6);
        test_Alist.addLast(6);
        assertEquals(expect_Alist.size(),test_Alist.size());
        assertEquals(expect_Alist.removeLast(),test_Alist.removeLast());
        assertEquals(expect_Alist.removeLast(),test_Alist.removeLast());
        assertEquals(expect_Alist.removeLast(),test_Alist.removeLast());
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> T = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                T.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size_t = T.size();
//                System.out.println("size: " + size);
                assertEquals(size,size_t);
            } else if (operationNumber == 2 & L.size() > 0){
                int last = L.getLast();
                int last_t = T.getLast();
//                System.out.println("last: " + last);
                assertEquals(last,last_t);
            } else if (operationNumber == 3 & L.size() > 0){
                int removelast_item = L.removeLast();
                int removelast_item_t = T.removeLast();
//                System.out.println("removeLast : " + removelast_item);
                assertEquals(removelast_item,removelast_item_t);
            }
        }
    }
}
