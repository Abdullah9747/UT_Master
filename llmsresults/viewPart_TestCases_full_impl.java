import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Vector;

public class VectorViewTest {

    @Test(expected = IndexException.class)
    public void testNegativeOffset() {
        Vector<Integer> data = new Vector<>();
        data.add(1);
        data.add(2);
        data.add(3);
        MyClass myObject = new MyClass(data, 0);
        myObject.viewPart(-1, 1);
    }

    @Test(expected = IndexException.class)
    public void testOffsetOutOfBounds() {
        Vector<Integer> data = new Vector<>();
        data.add(1);
        data.add(2);
        data.add(3);
        MyClass myObject = new MyClass(data, 0);
        myObject.viewPart(2, 2);
    }

    @Test
    public void testNormalCase() {
        Vector<Integer> data = new Vector<>();
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(5);
        MyClass myObject = new MyClass(data, 1); // this.offset = 1

        VectorView view = (VectorView) myObject.viewPart(1, 2); // offset = 1, length = 2
        //The view should start at index 2 (this.offset + offset = 1 + 1) and have length 2.
        assertNotNull(view);
        assertEquals(3, view.get(0)); // Check if the view contains the correct element at index 0
        assertEquals(4, view.get(1)); // Check if the view contains the correct element at index 1
    }

    static class MyClass { // Assuming the viewPart method is inside MyClass

        private Vector<Integer> vector;
        private int offset;

        public MyClass(Vector<Integer> vector, int offset) {
            this.vector = vector;
            this.offset = offset;
        }

        public Vector viewPart(int offset, int length) {
            if (offset < 0) {
                throw new IndexException(offset, size());
            }
            if (offset + length > size()) {
                throw new IndexException(offset + length, size());
            }
            return new VectorView(vector, offset + this.offset, length);
        }

        public int size() {
            return vector.size();
        }


    }

    static class VectorView extends Vector {
        private Vector vector;
        private int offset;
        private int length;

        public VectorView(Vector vector, int offset, int length) {
            this.vector = vector;
            this.offset = offset;
            this.length = length;
        }

        public Object get(int index){
            if(index < 0 || index >= length){
                throw new IndexOutOfBoundsException("Index out of bounds for VectorView");
            }
            return vector.get(offset + index);
        }
    }

    static class IndexException extends RuntimeException {
        public IndexException(int index, int size) {
            super("Index: " + index + ", Size: " + size);
        }
    }
}
