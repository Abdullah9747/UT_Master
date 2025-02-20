import org.junit.Test;
import static org.junit.Assert.*;

public class MyClassTest {

    static class MyClass {
        private int freeValue;

        public MyClass(int freeValue) {
            this.freeValue = freeValue;
        }

        public boolean isFree(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("Count cannot be negative");
            }
            return (free() >= count);
        }

        private int free() {
            return freeValue;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCount() {
        MyClass myObject = new MyClass(5);
        myObject.isFree(-1);
    }

    @Test
    public void testSufficientResourcesCountIsZero() {
        MyClass myObject = new MyClass(5);
        assertTrue(myObject.isFree(0));
    }

    @Test
    public void testSufficientResourcesCountEqualsFree() {
        MyClass myObject = new MyClass(5);
        assertTrue(myObject.isFree(5));
    }

    @Test
    public void testSufficientResourcesCountLessThanFree() {
        MyClass myObject = new MyClass(5);
        assertTrue(myObject.isFree(3));
    }

    @Test
    public void testInsufficientResources() {
        MyClass myObject = new MyClass(3);
        assertFalse(myObject.isFree(5));
    }

    @Test
    public void testInsufficientResourcesLargeCount() {
        MyClass myObject = new MyClass(0);
        assertFalse(myObject.isFree(100));
    }
}
