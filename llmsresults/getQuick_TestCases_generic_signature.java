import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testRWvtiaEqtz_zero() {
        assertEquals(1.0, Solution.RWvtiaEqtz(0), 0.0000001);
    }

    @Test
    public void testRWvtiaEqtz_positive() {
        assertEquals(120.0, Solution.RWvtiaEqtz(5), 0.0000001);
    }

    @Test
    public void testRWvtiaEqtz_negative() {
        assertEquals(1.0, Solution.RWvtiaEqtz(-1), 0.0000001);
    }
}
