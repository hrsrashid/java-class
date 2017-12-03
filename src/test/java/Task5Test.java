import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.BeforeClass;

public class Task5Test {

  static int[] expected;

  @BeforeClass() public static void setUp() {
     expected = new int[] {1, 2, 3, 4, 5};
  }

  @Test public void testSorted() {
    int[] a = new int[] {1, 2, 3, 4, 5};
    Task5.quicksort(a);
    assertArrayEquals(expected, a);
  }

  @Test public void testReversed() {
    int [] a = new int[] {5, 4, 3, 2, 1};
    Task5.quicksort(a);
    assertArrayEquals(expected, a);
  }

  @Test public void testIntermingled() {
    int[] a = new int[] {1, 3, 5, 2, 4};
    Task5.quicksort(a);
    assertArrayEquals(expected, a);
  }

  @Test public void testEmpty() {
    int[] a = new int[] { };
    Task5.quicksort(a);
    assertArrayEquals(new int[] {}, a);
  }

  @Test public void testOtherEdges() {
    int[] a = new int[] {0};
    Task5.quicksort(a);
    assertArrayEquals(new int[] {0}, a);

    a = new int[] {2, 1};
    Task5.quicksort(a);
    assertArrayEquals(new int[] {1, 2}, a);

    a = new int[] {2, 1, 3};
    Task5.quicksort(a);
    assertArrayEquals(new int[] {1, 2, 3}, a);
  }
}
