import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterators;

import static org.junit.Assert.*;

import java.util.Arrays;

public class BinNodeTest {

  private Node<String> leftChild;
  private Node<String> rightChild;
  private Node<String> classUnderTest;
  private Node<String> leaf;

  @Before() public void beforeEach() {
    leftChild = new BinNode<String>("left");
    rightChild = new BinNode<String>("right");
    classUnderTest = new BinNode<String>("root", leftChild, rightChild);
    leaf = new Leaf<String>();
  }

  @Test public void testSetNoChildren() {
    classUnderTest.setChildren(Arrays.asList());
    classUnderTest.getChildren().stream().forEach(n -> assertEquals(leaf, n));
  }

  @Test public void testRemoval() {
    classUnderTest.removeChild(leftChild);
    assertEquals(leaf, Iterators.get(classUnderTest.getChildren().iterator(), 0));
    assertEquals(rightChild, Iterators.get(classUnderTest.getChildren().iterator(), 1));
    classUnderTest.removeChild(rightChild);
    classUnderTest.getChildren().stream().forEach(n -> assertEquals(leaf, n));
  }

  @Test public void testHasLeaf() {
    assertFalse(classUnderTest.hasLeaf());
    assertTrue(leftChild.hasOnlyLeaves());

    leftChild.setChildren(Arrays.asList(new Leaf<String>(), new BinNode<String>("right left")));
    assertFalse(leftChild.hasOnlyLeaves());
    assertTrue(leftChild.hasLeaf());
  }
}
