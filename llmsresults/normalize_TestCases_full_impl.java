import org.junit.Test;
import static org.junit.Assert.*;

public class NormalizeTest {

    @Test
    public void testNormalize1() {
        assertEquals(10, normalize(10, 8));
    }

    @Test
    public void testNormalize2() {
        assertEquals(-56, normalize(200, 8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNormalize3() {
        normalize(300, 8);
    }

    @Test
    public void testNormalize4() {
        assertEquals(0, normalize(0, 8));
    }

    @Test
    public void testNormalize5() {
        assertEquals(-1, normalize(255, 8));
    }

    @Test
    public void testNormalize6() {
        assertEquals(127, normalize(127, 8));
    }

    @Test
    public void testNormalize7() {
        assertEquals(-1, normalize(65535, 16));
    }

    @Test
    public void testNormalize8() {
        assertEquals(-32768, normalize(32768, 16));
    }

    @Test
    public void testNormalize9() {
        assertEquals(32767, normalize(32767, 16));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNormalize10() {
        normalize(100000, 16);
    }

    @Test
    public void testNormalize11() {
        assertEquals(2147483647, normalize(2147483647, 32));
    }

    @Test
    public void testNormalize12() {
        assertEquals(-1, normalize(4294967295, 32));
    }

    public static int normalize(int i, int bitCount) {
        int max = 0xffffffff >>> (32 - bitCount);
        if (i > max)
            throw new IllegalArgumentException("The integer cannot fit to bit boundaries.");
        if (i > (max >>> 1))
            return i - (max + 1);
        else
            return i;
    }
}
