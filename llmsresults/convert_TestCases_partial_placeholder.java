import org.junit.Test;
import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void testConvert_zero() {
        Solution solution = new Solution();
        assertEquals("0", solution.convert(0));
    }

    @Test
    public void testConvert_one() {
        Solution solution = new Solution();
        assertEquals("1", solution.convert(1));
    }

    @Test
    public void testConvert_two() {
        Solution solution = new Solution();
        assertEquals("10", solution.convert(2));
    }

    @Test
    public void testConvert_ten() {
        Solution solution = new Solution();
        assertEquals("1010", solution.convert(10));
    }
}
