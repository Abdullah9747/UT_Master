import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Vector;

public class ViewPartTest {

    @Test
    public void testViewPartNormalCase() {
        Vector<String> myVector = new Vector<>();
        myVector.add("A");
        myVector.add("B");
        myVector.add("C");
        myVector.add("D");
        myVector.add("E");

        Vector<String> result = (Vector<String>) myVector.viewPart(0, 5);
        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));
        assertEquals("C", result.get(2));
        assertEquals("D", result.get(3));
        assertEquals("E", result.get(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testViewPartNegativeOffset() {
        Vector<String> myVector = new Vector<>();
        myVector.add("A");
        myVector.add("B");
        myVector.add("C");
        myVector.add("D");
        myVector.add("E");
        myVector.viewPart(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testViewPartNegativeLength() {
        Vector<String> myVector = new Vector<>();
        myVector.add("A");
        myVector.add("B");
        myVector.add("C");
        myVector.add("D");
        myVector.add("E");
        myVector.viewPart(0, -1);
    }

    @Test
    public void testViewPartOffsetAndLengthWithinBounds() {
        Vector<String> myVector = new Vector<>();
        myVector.add("A");
        myVector.add("B");
        myVector.add("C");
        myVector.add("D");
        myVector.add("E");

        Vector<String> result = (Vector<String>) myVector.viewPart(1, 3);
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("B", result.get(0));
        assertEquals("C", result.get(1));
        assertEquals("D", result.get(2));
    }

    @Test
    public void testViewPartOffsetAtEnd() {
        Vector<String> myVector = new Vector<>();
        myVector.add("A");
        myVector.add("B");
        myVector.add("C");
        myVector.add("D");
        myVector.add("E");

        Vector<String> result = (Vector<String>) myVector.viewPart(4, 1);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("E", result.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testViewPartOffsetOutOfBounds() {
        Vector<String> myVector = new Vector<>();
        myVector.add("A");
        myVector.add("B");
        myVector.add("C");
        myVector.add("D");
        myVector.add("E");
        myVector.viewPart(5, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testViewPartOffsetPlusLengthOutOfBounds() {
        Vector<String> myVector = new Vector<>();
        myVector.add("A");
        myVector.add("B");
        myVector.add("C");
        myVector.add("D");
        myVector.add("E");
        myVector.viewPart(3, 3);
    }

    @Test
    public void testViewPartEmptyVector() {
        Vector<String> myVector = new Vector<>();
        Vector<String> result = (Vector<String>) myVector.viewPart(0, 0);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testViewPartEmptyVectorOffsetOutOfBounds() {
        Vector<String> myVector = new Vector<>();
        myVector.viewPart(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testViewPartEmptyVectorNegativeOffset() {
        Vector<String> myVector = new Vector<>();
        myVector.viewPart(-1, 0);
    }
}
