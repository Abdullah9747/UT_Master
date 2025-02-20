import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TxKIRIZmUITest {

    @Test
    public void testBasicMultiplication() {
        assertEquals(6, TxKIRIZmUI(2, 3));
    }

    @Test
    public void testMultiplicationByZero() {
        assertEquals(0, TxKIRIZmUI(5, 0));
    }

    @Test
    public void testMultiplicationByOne() {
        assertEquals(7, TxKIRIZmUI(7, 1));
    }

    @Test
    public void testNegativeNumbers() {
        assertEquals(-8, TxKIRIZmUI(-4, 2));
    }

    @Test
    public void testTwoNegativeNumbers() {
        assertEquals(15, TxKIRIZmUI(-3, -5));
    }

    @Test
    public void testLargeNumbers() {
        assertEquals(-2, TxKIRIZmUI(2147483647, 2));
    }

    private int TxKIRIZmUI(int BLRerNbefJ, int MuCxdmtXtY) {
        return BLRerNbefJ * MuCxdmtXtY;
    }
}
