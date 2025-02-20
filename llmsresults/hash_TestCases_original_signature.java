import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HashTest {

    @Test
    public void testHash1() {
        assertEquals(32, hash(1, 1));
    }

    @Test
    public void testHash2() {
        assertEquals(160, hash(10, 5));
    }

    @Test
    public void testHash3() {
        assertEquals(-32, hash(-1, -1));
    }

    @Test
    public void testHash4() {
        assertEquals(1, hash(0, 1));
    }

    @Test
    public void testHash5() {
        assertEquals(-31, hash(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void testHash6() {
        assertEquals(31, hash(Integer.MIN_VALUE, Integer.MIN_VALUE));
    }

    private int hash(int data, int seed) {
        int result = seed;
        result = 31 * result + data;
        return result;
    }
}
