import org.junit.Test;
import static org.junit.Assert.*;

public class SolutionTest {

    Solution solution = new Solution();

    @Test
    public void testConvert_3() {
        assertEquals("Pling", solution.convert(3));
    }

    @Test
    public void testConvert_5() {
        assertEquals("Plang", solution.convert(5));
    }

    @Test
    public void testConvert_7() {
        assertEquals("Plong", solution.convert(7));
    }

    @Test
    public void testConvert_15() {
        assertEquals("PlingPlang", solution.convert(15));
    }

    @Test
    public void testConvert_21() {
        assertEquals("PlingPlong", solution.convert(21));
    }

    @Test
    public void testConvert_35() {
        assertEquals("PlangPlong", solution.convert(35));
    }

    @Test
    public void testConvert_105() {
        assertEquals("PlingPlangPlong", solution.convert(105));
    }

    @Test
    public void testConvert_2() {
        assertEquals("2", solution.convert(2));
    }
}
