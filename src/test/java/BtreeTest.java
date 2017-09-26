import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

public class BtreeTest {
    @Test public void testBtreeIteratesAllOver() {
        Btree<Integer> classUnderTest = new Btree<Integer>();
        assertFalse("shoudn't have next node", classUnderTest.iterator().hasNext());

        Node<Integer> root = new BinNode<Integer>(0);
        classUnderTest.setRoot(root);
        root.setChildren(Arrays.asList(new BinNode<Integer>(2, new BinNode<Integer>(4), new BinNode<Integer>(3)), new BinNode<Integer>(1)));
        Iterator<Node<Integer>> it = classUnderTest.iterator();

        for (Integer i = 0; i < 5; i++) {
            assertTrue(it.hasNext());
            assertEquals(i, it.next().getValue());
        }
    }
}
