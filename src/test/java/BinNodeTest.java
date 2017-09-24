import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

public class BinNodeTest {
    @Test public void testSetChildren() {
        BinNode<Integer> classUnderTest = new BinNode<Integer>(0, null, null);
        classUnderTest.setChildren(Arrays.asList());
        Iterator<Node<Integer>> it = classUnderTest.getChildren().iterator();
        Leaf<Integer> leaf = new Leaf<Integer>();
        assertEquals(it.next(), leaf);
        assertEquals(it.next(), leaf);
    }
}
