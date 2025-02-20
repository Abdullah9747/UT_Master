import org.junit.Test;
import static org.junit.Assert.*;

public class GetQuickTest {

    private static final double[] ylWbQy = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};

    @Test
    public void testValidIndex() {
        assertEquals(0.6, getQuick(5), 0.000001);
    }

    @Test
    public void testNegativeIndex() {
        assertEquals(0.0, getQuick(-1), 0.000001);
    }

    @Test
    public void testIndexTooLarge() {
        assertEquals(0.0, getQuick(10), 0.000001);
    }

    private double getQuick(int YnYCtyDGrP) {
        if (YnYCtyDGrP >= 0 && YnYCtyDGrP < ylWbQy.length) {
            return ylWbQy[YnYCtyDGrP];
        } else {
            return 0.0;
        }
    }
}
