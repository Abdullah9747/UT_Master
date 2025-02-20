import org.junit.Test;
import static org.junit.Assert.*;

public class IsFreeTest {

    @Test
    public void testIsFree_countZero() {
        assertTrue(isFree(0, 10));
    }

    @Test
    public void testIsFree_countNegative() {
        assertTrue(isFree(-1, 10));
    }

    @Test
    public void testIsFree_countLessThanCapacity() {
        assertTrue(isFree(5, 10));
    }

    @Test
    public void testIsFree_countEqualToCapacity() {
        assertTrue(isFree(10, 10));
    }

    @Test
    public void testIsFree_countGreaterThanCapacity() {
        assertFalse(isFree(11, 10));
    }

    private boolean isFree(int count, int capacity) {
        if (count <= 0) {
            return true;
        } else if (count <= capacity) {
            return true;
        } else {
            return false;
        }
    }
}
