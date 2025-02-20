import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NormalizeTest {

    @Test
    public void testNormalize_10_4() {
        assertEquals(10, normalize(10, 4));
    }

    @Test
    public void testNormalize_20_4() {
        assertEquals(4, normalize(20, 4));
    }

    @Test
    public void testNormalize_0_4() {
        assertEquals(0, normalize(0, 4));
    }

    @Test
    public void testNormalize_Neg1_4() {
        assertEquals(15, normalize(-1, 4));
    }

    @Test
    public void testNormalize_65535_16() {
        assertEquals(65535, normalize(65535, 16));
    }

    @Test
    public void testNormalize_1_0() {
        assertEquals(0, normalize(1, 0));
    }

    @Test
    public void testNormalize_Neg10_4() {
        assertEquals(6, normalize(-10, 4));
    }

    @Test
    public void testNormalize_256_8() {
        assertEquals(0, normalize(256, 8));
    }

    @Test
    public void testNormalize_255_8() {
        assertEquals(255, normalize(255, 8));
    }

    private int normalize(int i, int bitCount) {
        int mask = (1 << bitCount) - 1;
        return i & mask;
    }
}
