import org.junit.Test;
import static org.junit.Assert.*;

public class GetQuickTest {

    @Test
    public void testGetQuickValidIndex() {
        double[] elements = {1.0, 2.0, 3.0};
        GetQuick obj = new GetQuick(elements);
        assertEquals(1.0, obj.getQuick(0), 0.0001);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetQuickNegativeIndex() {
        double[] elements = {1.0, 2.0, 3.0};
        GetQuick obj = new GetQuick(elements);
        obj.getQuick(-1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetQuickIndexTooLarge() {
        double[] elements = {1.0, 2.0, 3.0};
        GetQuick obj = new GetQuick(elements);
        obj.getQuick(3);
    }

    @Test
    public void testGetQuickAnotherValidIndex() {
        double[] elements = {1.0, 2.0, 3.0};
        GetQuick obj = new GetQuick(elements);
        assertEquals(3.0, obj.getQuick(2), 0.0001);
    }

    private static class GetQuick {
        private double[] elements;

        public GetQuick(double[] elements) {
            this.elements = elements;
        }

        public double getQuick(int index) {
            return elements[index];
        }
    }
}
