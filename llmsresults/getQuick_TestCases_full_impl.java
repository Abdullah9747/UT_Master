import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GetQuickTest {

    private DenseVector vector;
    private int offset;

    @Before
    public void setUp() {
        vector = new DenseVector(10);
        for (int i = 0; i < 10; i++) {
            vector.setQuick(i, i * 1.0); // Initialize with some values
        }
        offset = 0; // Initialize offset to 0, can be modified in individual tests
    }

    @Test
    public void testGetQuick_firstElement() {
        offset = 0;
        assertEquals(0.0, new TestClass(vector, offset).getQuick(0), 0.0);
    }

    @Test
    public void testGetQuick_lastElement() {
        offset = 0;
        assertEquals(9.0, new TestClass(vector, offset).getQuick(9), 0.0);
    }

    @Test
    public void testGetQuick_middleElement() {
        offset = 0;
        assertEquals(5.0, new TestClass(vector, offset).getQuick(5), 0.0);
    }

    @Test
    public void testGetQuick_nonZeroOffsetFirstElement() {
        offset = 5;
        assertEquals(5.0, new TestClass(vector, offset).getQuick(0), 0.0);
    }

    @Test
    public void testGetQuick_nonZeroOffsetLastElement() {
        offset = 5;
        assertEquals(9.0, new TestClass(vector, offset).getQuick(4), 0.0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetQuick_offsetPlusIndexEqualsSize() {
        offset = 5;
        new TestClass(vector, offset).getQuick(5);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetQuick_negativeIndex() {
        offset = 0;
        new TestClass(vector, offset).getQuick(-1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetQuick_negativeOffset() {
        offset = -1;
        new TestClass(vector, offset).getQuick(0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetQuick_indexEqualsSize() {
        offset = 0;
        new TestClass(vector, offset).getQuick(10);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetQuick_offsetEqualsSize() {
        offset = 10;
        new TestClass(vector, offset).getQuick(0);
    }

    @Test
    public void testGetQuick_negativeOffsetWithinBounds() {
        offset = -5;
        assertEquals(0.0, new TestClass(vector, offset).getQuick(5), 0.0);
    }

    @Test
    public void testGetQuick_negativeOffsetWithinBounds2() {
        offset = -5;
        assertEquals(1.0, new TestClass(vector, offset).getQuick(6), 0.0);
    }

    @Test
    public void testGetQuick_negativeOffsetWithinBounds3() {
        offset = -5;
        assertEquals(4.0, new TestClass(vector, offset).getQuick(9), 0.0);
    }

    @Test
    public void testGetQuick_negativeOffsetWithinBounds4() {
        offset = -5;
        assertEquals(5.0, new TestClass(vector, offset).getQuick(10), 0.0);
    }

    @Test
    public void testGetQuick_negativeOffsetWithinBounds5() {
        offset = -5;
        assertEquals(9.0, new TestClass(vector, offset).getQuick(14), 0.0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetQuick_negativeOffsetOutOfBounds() {
        offset = -5;
        new TestClass(vector, offset).getQuick(15);
    }

    // Dummy DenseVector class for testing.
    static class DenseVector {
        private double[] data;

        public DenseVector(int size) {
            data = new double[size];
        }

        public double getQuick(int index) {
            return data[index];
        }

        public void setQuick(int index, double value) {
            data[index] = value;
        }

        public int size() {
            return data.length;
        }
    }

    // Test class that contains the getQuick method.
    static class TestClass {
        private DenseVector vector;
        private int offset;

        public TestClass(DenseVector vector, int offset) {
            this.vector = vector;
            this.offset = offset;
        }

        public double getQuick(int index) {
            return vector.getQuick(offset + index);
        }
    }
}
