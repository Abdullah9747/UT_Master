import org.junit.Test;
import static org.junit.Assert.*;

public class FactorialTest {

    @Test
    public void testFactorialZero() {
        assertEquals(0, Factorial.factorial(0));
    }

    @Test
    public void testFactorialOne() {
        assertEquals(1, Factorial.factorial(1));
    }

    @Test
    public void testFactorialPositive() {
        assertEquals(120, Factorial.factorial(5));
    }


}
