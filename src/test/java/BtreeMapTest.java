import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Map;
import org.junit.Before;

public class BtreeMapTest {
    private Map<String, Integer> classUnderTest;

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

    @Test public void testDeletion() {
      assertNull("should return null when removing non-existing entry", classUnderTest.remove("a"));
      classUnderTest.put("a", 1);
      classUnderTest.put("b", 2);
      classUnderTest.put("c", 3);
      classUnderTest.put("d", 4);
      classUnderTest.put("e", 5);
      assertTrue("should countain key 'a' now", classUnderTest.containsKey("a"));
      assertEquals("should return value 1 of 'a'", Integer.valueOf(1), classUnderTest.remove("a"));
      assertFalse("shouldn't contain 'a' key anymore'", classUnderTest.containsKey("a"));
      assertEquals("should return value 2 of 'b'", Integer.valueOf(2), classUnderTest.remove("b"));
      assertEquals("should return value 5 of 'e'", Integer.valueOf(5), classUnderTest.remove("e"));
      assertEquals("should return value 4 of 'd'", Integer.valueOf(4), classUnderTest.remove("d"));
      assertEquals("should return value 3 of 'c'", Integer.valueOf(3), classUnderTest.remove("c"));
      assertTrue("should become empty", classUnderTest.isEmpty());
    }
}
