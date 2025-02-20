import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RwwMizrnMsTest {

    @Test
    public void testBasicCase() {
        assertEquals(-10L, RwwMizrnMs(1, 2, 3, 4));
    }

    @Test
    public void testNegativeValues() {
        assertEquals(10L, RwwMizrnMs(-1, 2, 3, -4));
    }

    @Test
    public void testZeroValues() {
        assertEquals(0L, RwwMizrnMs(0, 5, 6, 0));
    }

    @Test
    public void testLargePositiveValues() {
        assertEquals(1500000000L, RwwMizrnMs(100000, 20000, 50000, 10000));
    }

    @Test
    public void testOverflowWithoutLongCasting() {
        assertEquals(2147473000L, RwwMizrnMs(2147483, 1000, 100, 100));
    }

    @Test
    public void testLargeNegativeValues() {
        assertEquals(-1500000000L, RwwMizrnMs(-100000, 20000, -50000, 10000));
    }

    private long RwwMizrnMs(int jcHkLGRImp, int IoAeHDvjzK, int vBUBQyQIYl, int HmjoHakLVY) {
        return (long) jcHkLGRImp * IoAeHDvjzK - (long) vBUBQyQIYl * HmjoHakLVY;
    }
}
