import org.junit.Test;
import static org.junit.Assert.*;

public class OKMBzVPzrnTest {

    @Test
    public void testCase1() {
        assertEquals("6", oKMBzVPzrn(1, 2, 3));
    }

    @Test
    public void testCase2() {
        assertEquals("0", oKMBzVPzrn(0, 0, 0));
    }

    @Test
    public void testCase3() {
        assertEquals("-6", oKMBzVPzrn(-1, -2, -3));
    }

    @Test
    public void testCase4() {
        assertEquals("600", oKMBzVPzrn(100, 200, 300));
    }

    @Test
    public void testCase5() {
        assertEquals("3", oKMBzVPzrn(1, 1, 1));
    }

    @Test
    public void testCase6() {
        assertEquals("15", oKMBzVPzrn(5, 2, 8));
    }

    @Test
    public void testCase7() {
        assertEquals("15", oKMBzVPzrn(8, 2, 5));
    }

    @Test
    public void testCase8() {
        assertEquals("15", oKMBzVPzrn(2, 8, 5));
    }

    @Test
    public void testCase9() {
        assertEquals("15", oKMBzVPzrn(5, 8, 2));
    }

    @Test
    public void testCase10() {
        assertEquals("15", oKMBzVPzrn(5, 2, 8));
    }

    @Test
    public void testCase11() {
        assertEquals("0", oKMBzVPzrn(-1, 0, 1));
    }

    @Test
    public void testCase12() {
        assertEquals("-2147483646", oKMBzVPzrn(Integer.MAX_VALUE, 1, 1));
    }

    @Test
    public void testCase13() {
        assertEquals("2147483646", oKMBzVPzrn(Integer.MIN_VALUE, 1, 1));
    }

    @Test
    public void testCase14() {
        assertEquals("-2147483646", oKMBzVPzrn(1, Integer.MAX_VALUE, 1));
    }

    @Test
    public void testCase15() {
        assertEquals("2147483646", oKMBzVPzrn(1, Integer.MIN_VALUE, 1));
    }

    @Test
    public void testCase16() {
        assertEquals("-2147483646", oKMBzVPzrn(1, 1, Integer.MAX_VALUE));
    }

    @Test
    public void testCase17() {
        assertEquals("2147483646", oKMBzVPzrn(1, 1, Integer.MIN_VALUE));
    }

    @Test
    public void testCase18() {
        assertEquals("2147483648", oKMBzVPzrn(-1, Integer.MAX_VALUE, Integer.MIN_VALUE));
    }

    private String oKMBzVPzrn(int yFdSNgZdFo, int SXoBuJRodU, int zFhCqslUis) {
        return String.valueOf((long)yFdSNgZdFo + SXoBuJRodU + zFhCqslUis);
    }
}
