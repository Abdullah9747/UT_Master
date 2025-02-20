import org.junit.Test;
import static org.junit.Assert.*;

public class LmfrutoKypTest {

    // Scenario 2.1: Returns the input value itself.
    @Test
    public void testReturnsInputZero() {
        assertEquals(0, new MyClass().lmfrutoKyp(0));
    }

    @Test
    public void testReturnsInputOne() {
        assertEquals(1, new MyClass().lmfrutoKyp(1));
    }

    @Test
    public void testReturnsInputNegativeOne() {
        assertEquals(-1, new MyClass().lmfrutoKyp(-1));
    }

    // Scenario 2.2: Returns the absolute value of the input.
    @Test
    public void testAbsoluteValuePositive() {
        assertEquals(5, new MyClass().lmfrutoKyp(5));
    }

    @Test
    public void testAbsoluteValueNegative() {
        assertEquals(5, new MyClass().lmfrutoKyp(-5));
    }

    @Test
    public void testAbsoluteValueZero() {
        assertEquals(0, new MyClass().lmfrutoKyp(0));
    }

    // Scenario 2.3: Returns the input plus 1.
    @Test
    public void testInputPlusOneZero() {
        assertEquals(1, new MyClass().lmfrutoKyp(0));
    }

    @Test
    public void testInputPlusOneOne() {
        assertEquals(2, new MyClass().lmfrutoKyp(1));
    }

    @Test
    public void testInputPlusOneNegativeOne() {
        assertEquals(0, new MyClass().lmfrutoKyp(-1));
    }

    // Scenario 3.1: Returns true if positive, false otherwise.
    @Test
    public void testPositiveReturnsTrue() {
        assertTrue(new MyClass().lmfrutoKyp(1));
    }

    @Test
    public void testZeroReturnsFalse() {
        assertFalse(new MyClass().lmfrutoKyp(0));
    }

    @Test
    public void testNegativeReturnsFalse() {
        assertFalse(new MyClass().lmfrutoKyp(-1));
    }

    // Scenario 3.2: Returns true if even, false otherwise.
    @Test
    public void testEvenReturnsTrue() {
        assertTrue(new MyClass().lmfrutoKyp(2));
    }

    @Test
    public void testOddReturnsFalse() {
        assertFalse(new MyClass().lmfrutoKyp(3));
    }

    @Test
    public void testZeroReturnsTrueEven() {
        assertTrue(new MyClass().lmfrutoKyp(0));
    }

    @Test
    public void testNegativeEvenReturnsTrue() {
        assertTrue(new MyClass().lmfrutoKyp(-2));
    }

    @Test
    public void testNegativeOddReturnsFalse() {
        assertFalse(new MyClass().lmfrutoKyp(-3));
    }

    //Added a dummy class to make the code compilable

    class MyClass {
        public int lmfrutoKyp(int OgcGuGPecy) {
            return OgcGuGPecy;
        }
        public boolean lmfrutoKyp(int OgcGuGPecy) {
            return OgcGuGPecy > 0;
        }
    }
}
