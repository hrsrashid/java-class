import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class BtreeMapTest {
    private BtreeMap<String, Integer> classUnderTest;

    @Before() public void beforeEach() {
      classUnderTest = new BtreeMap<String, Integer>();
    }

    @Test public void testEmpty() {
      assertTrue(classUnderTest.isEmpty());
      assertNull(classUnderTest.put("a", 1));
      assertFalse(classUnderTest.isEmpty());
    }
  
    @Test public void testInsertAndSearch() {
      assertNull(classUnderTest.put("b", 2));
      assertNull(classUnderTest.put("d", 4));
      assertNull(classUnderTest.put("a", 1));
      assertEquals(Integer.valueOf(1), classUnderTest.get("a"));
      assertNull(classUnderTest.put("c", 3));
      assertEquals(Integer.valueOf(2), classUnderTest.get("b"));
      assertNull(classUnderTest.put("e", 5));
      assertEquals(Integer.valueOf(3), classUnderTest.get("c"));
      assertEquals(Integer.valueOf(4), classUnderTest.get("d"));
      assertEquals(Integer.valueOf(2), classUnderTest.put("b", 99));
      assertEquals(Integer.valueOf(5), classUnderTest.get("e"));
      assertEquals(Integer.valueOf(99), classUnderTest.get("b"));
    }
}
