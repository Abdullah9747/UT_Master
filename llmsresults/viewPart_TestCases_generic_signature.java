import org.junit.Test;
import java.util.Vector;
import static org.junit.Assert.*;

public class UPJvOLXBklTest {

    @Test
    public void testUPJvOLXBkl_Scenario1_Case1() {
        Vector<Integer> result = uPJvOLXBkl(0, 0);
        assertEquals(0, result.capacity());
    }

    @Test
    public void testUPJvOLXBkl_Scenario1_Case2() {
        Vector<Integer> result = uPJvOLXBkl(5, 3);
        assertEquals(8, result.capacity());
    }

    @Test
    public void testUPJvOLXBkl_Scenario1_Case3() {
        Vector<Integer> result = uPJvOLXBkl(-5, 10);
        assertEquals(5, result.capacity());
    }

    @Test
    public void testUPJvOLXBkl_Scenario1_Case4() {
        Vector<Integer> result = uPJvOLXBkl(10, -5);
        assertEquals(5, result.capacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUPJvOLXBkl_Scenario1_Case5() {
        uPJvOLXBkl(-10, -5);
    }

    @Test
    public void testUPJvOLXBkl_Scenario2_Case1() {
        Vector<Integer> result = uPJvOLXBkl(0, 0);
        assertEquals(0, result.size());
    }

    @Test
    public void testUPJvOLXBkl_Scenario2_Case2() {
        Vector<Integer> result = uPJvOLXBkl(3, 2);
        Vector<Integer> expected = new Vector<>();
        expected.add(0);
        expected.add(2);
        expected.add(4);
        assertEquals(expected, result);
    }

    @Test
    public void testUPJvOLXBkl_Scenario2_Case3() {
        Vector<Integer> result = uPJvOLXBkl(1, -5);
        Vector<Integer> expected = new Vector<>();
        expected.add(0);
        assertEquals(expected, result);
    }

    @Test
    public void testUPJvOLXBkl_Scenario2_Case4() {
        Vector<Integer> result = uPJvOLXBkl(5, 0);
        Vector<Integer> expected = new Vector<>();
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(0);
        assertEquals(expected, result);
    }

    @Test
    public void testUPJvOLXBkl_Scenario2_Case5() {
        Vector<Integer> result = uPJvOLXBkl(-1, 10);
        assertEquals(0, result.size());
    }

    private Vector<Integer> uPJvOLXBkl(int BHoBVfpIFL, int XzPFlyfFiD) {
        try {
            Vector<Integer> result = new Vector<>(BHoBVfpIFL + XzPFlyfFiD);
            return result;
        } catch (IllegalArgumentException e) {
             if (BHoBVfpIFL + XzPFlyfFiD < 0) {
                 throw e;
             }
             Vector<Integer> result = new Vector<>();
             for (int i = 0; i < BHoBVfpIFL; i++) {
                 result.add(i * XzPFlyfFiD);
             }
             return result;
        }
    }
}
