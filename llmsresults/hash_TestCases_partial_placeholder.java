import org.junit.Test;
import static org.junit.Assert.*;

public class HashTest {

    @Test
    public void testBasicValues() {
        assertEquals(961, hash(31, 0)); // Simple case with first input = 31, second = 0
        assertEquals(32, hash(1, 1));   // Simple case with both inputs as small values
        assertEquals(63, hash(1, 32));   // Simple case with both inputs as small values
        assertEquals(31, hash(0, 31));   // Simple case with first input = 0, second = 31
    }

    @Test
    public void testZeroValues() {
        assertEquals(0, hash(0, 0));   // Both inputs are zero
    }

    @Test
    public void testLargePositiveValues() {
        assertEquals(65567, hash(2147, 1));  //First int is large, second small.
        assertEquals(-1396221842, hash(13235, 1000000000)); // Large first int, large second int.
    }

    @Test
    public void testNegativeValues() {
        assertEquals(-31, hash(-1, 0));  // First input is negative, second is zero
        assertEquals(-32, hash(-1, -1)); // Both inputs are negative
        assertEquals(-63, hash(-1, -32));
        assertEquals(-2, hash(0, -2));   // First input is zero, second is negative
    }

    @Test
    public void testMixedPositiveNegative() {
        assertEquals(0, hash(1, -31));  // First positive, second negative, result zero
        assertEquals(-30, hash(-1, 31)); // First negative, second positive, result around zero
    }

    @Test
    public void testOverflowPotential() {
        assertEquals(-2147483648, hash(69273, 1));  //First is a value that when multiplied by 31 causes overflow to negative.
        assertEquals(2147483647, hash(69273, 2147483647)); // Second input is max int value
    }

    @Test
    public void testLargeValuesNearIntegerLimit() {
        assertEquals(-1, hash(Integer.MAX_VALUE, 0)); // First input is max int
        assertEquals(2147452831, hash(1000000, Integer.MAX_VALUE)); // Second input is max int
        assertEquals(0, hash(Integer.MIN_VALUE, 0));  // First input is min int
        assertEquals(-2147483648, hash(Integer.MIN_VALUE, 1));  // First input is min int and second is 1
    }

    static int hash(int fHtQysfcbx, int ASQRpofADq) {
        long h = (long)fHtQysfcbx * 31;
        h = h + ASQRpofADq;
        h = h ^ (h >> 16);
        return (int)h;
    }
}
