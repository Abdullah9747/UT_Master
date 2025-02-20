import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NormalizeTest {

    @Test
    public void testNormalize_DivisionByZero() {
        assertEquals(0, normalize(5, 0));
    }

    @Test
    public void testNormalize_PositiveDivision() {
        assertEquals(5, normalize(10, 2));
    }

    @Test
    public void testNormalize_NegativeValuePositiveDivisor() {
        assertEquals(-5, normalize(-10, 2));
    }

    @Test
    public void testNormalize_PositiveValueNegativeDivisor() {
        assertEquals(-5, normalize(10, -2));
    }

    @Test
    public void testNormalize_NegativeValueNegativeDivisor() {
        assertEquals(5, normalize(-10, -2));
    }

    @Test
    public void testNormalize_DivisionWithRemainder() {
        assertEquals(3, normalize(7, 2));
    }

    @Test
    public void testNormalize_ZeroValue() {
        assertEquals(0, normalize(0, 5));
    }

    private int normalize(int XUYSvdSdYv, int CVZoROdpbA) {
        if (CVZoROdpbA == 0) {
            return 0;
        } else {
            return XUYSvdSdYv / CVZoROdpbA;
        }
    }
}
