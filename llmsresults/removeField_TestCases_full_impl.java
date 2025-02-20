import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RemoveFieldTest {

    private RemoveFieldClass testObject; // Replace with your actual class name
    private int[] offsets;
    private int[] lengths;
    private Object[] readFields;
    private Object[] writeFields;
    private int numFields;
    private final int NULL_INDICATOR_OFFSET = -1; // Example Value, replace if different

    @Before
    public void setUp() {
        testObject = new RemoveFieldClass(); // Replace with your actual class name

    }

    //Helper class to access removeField method
    class RemoveFieldClass {
        public int[] offsets;
        public int[] lengths;
        public Object[] readFields;
        public Object[] writeFields;
        public int numFields;

        public void setNumFields(int numFields) {
            this.numFields = numFields;
        }

        public void markModified(int fieldNum) {
            //Dummy implementation for testing purposes
        }

        public void removeField(int fieldNum) {
            // range check
            if (fieldNum < 0 || fieldNum >= this.numFields) {
                throw new IndexOutOfBoundsException();
            }
            int lastIndex = this.numFields - 1;

            if (fieldNum < lastIndex) {
                int len = lastIndex - fieldNum;
                System.arraycopy(this.offsets, fieldNum + 1, this.offsets, fieldNum, len);
                System.arraycopy(this.lengths, fieldNum + 1, this.lengths, fieldNum, len);
                System.arraycopy(this.readFields, fieldNum + 1, this.readFields, fieldNum, len);
                System.arraycopy(this.writeFields, fieldNum + 1, this.writeFields, fieldNum, len);
                markModified(fieldNum);
            }
            this.offsets[lastIndex] = NULL_INDICATOR_OFFSET;
            this.lengths[lastIndex] = 0;
            this.writeFields[lastIndex] = null;

            setNumFields(lastIndex);
        }
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveField_InvalidFieldNum_Negative() {
        testObject.numFields = 5;
        testObject.offsets = new int[5];
        testObject.lengths = new int[5];
        testObject.readFields = new Object[5];
        testObject.writeFields = new Object[5];

        int fieldNum = -1;
        testObject.removeField(fieldNum);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveField_InvalidFieldNum_TooLarge() {
        testObject.numFields = 5;
        testObject.offsets = new int[5];
        testObject.lengths = new int[5];
        testObject.readFields = new Object[5];
        testObject.writeFields = new Object[5];

        int fieldNum = 5;
        testObject.removeField(fieldNum);
    }

    @Test
    public void testRemoveField_RemovingLastField() {
        testObject.numFields = 5;
        testObject.offsets = new int[5];
        testObject.lengths = new int[5];
        testObject.readFields = new Object[5];
        testObject.writeFields = new Object[5];

        int fieldNum = 4;
        testObject.removeField(fieldNum);

        assertEquals(NULL_INDICATOR_OFFSET, testObject.offsets[4]);
        assertEquals(0, testObject.lengths[4]);
        assertNull(testObject.writeFields[4]);
        assertEquals(4, testObject.numFields);
    }

    @Test
    public void testRemoveField_RemovingFieldInMiddle() {
        testObject.numFields = 5;
        testObject.offsets = new int[] {10, 20, 30, 40, 50};
        testObject.lengths = new int[] {1, 2, 3, 4, 5};
        testObject.readFields = new Object[] {new Object(), new Object(), new Object(), new Object(), new Object()};
        testObject.writeFields = new Object[] {new Object(), new Object(), new Object(), new Object(), new Object()};

        int fieldNum = 2;
        testObject.removeField(fieldNum);

        assertEquals(10, testObject.offsets[0]);
        assertEquals(20, testObject.offsets[1]);
        assertEquals(40, testObject.offsets[2]);
        assertEquals(50, testObject.offsets[3]);
        assertEquals(NULL_INDICATOR_OFFSET, testObject.offsets[4]);

        assertEquals(1, testObject.lengths[0]);
        assertEquals(2, testObject.lengths[1]);
        assertEquals(4, testObject.lengths[2]);
        assertEquals(5, testObject.lengths[3]);
        assertEquals(0, testObject.lengths[4]);

        assertNotNull(testObject.readFields[0]);
        assertNotNull(testObject.readFields[1]);
        assertNotNull(testObject.readFields[2]);
        assertNotNull(testObject.readFields[3]);

        assertNotNull(testObject.writeFields[0]);
        assertNotNull(testObject.writeFields[1]);
        assertNotNull(testObject.writeFields[2]);
        assertNotNull(testObject.writeFields[3]);
        assertNull(testObject.writeFields[4]);

        assertEquals(4, testObject.numFields);
    }

    @Test
    public void testRemoveField_RemovingFirstField() {
        testObject.numFields = 5;
        testObject.offsets = new int[] {10, 20, 30, 40, 50};
        testObject.lengths = new int[] {1, 2, 3, 4, 5};
        testObject.readFields = new Object[] {new Object(), new Object(), new Object(), new Object(), new Object()};
        testObject.writeFields = new Object[] {new Object(), new Object(), new Object(), new Object(), new Object()};

        int fieldNum = 0;
        testObject.removeField(fieldNum);

        assertEquals(20, testObject.offsets[0]);
        assertEquals(30, testObject.offsets[1]);
        assertEquals(40, testObject.offsets[2]);
        assertEquals(50, testObject.offsets[3]);
        assertEquals(NULL_INDICATOR_OFFSET, testObject.offsets[4]);

        assertEquals(2, testObject.lengths[0]);
        assertEquals(3, testObject.lengths[1]);
        assertEquals(4, testObject.lengths[2]);
        assertEquals(5, testObject.lengths[3]);
        assertEquals(0, testObject.lengths[4]);

        assertNotNull(testObject.readFields[0]);
        assertNotNull(testObject.readFields[1]);
        assertNotNull(testObject.readFields[2]);
        assertNotNull(testObject.readFields[3]);

        assertNotNull(testObject.writeFields[0]);
        assertNotNull(testObject.writeFields[1]);
        assertNotNull(testObject.writeFields[2]);
        assertNotNull(testObject.writeFields[3]);
        assertNull(testObject.writeFields[4]);

        assertEquals(4, testObject.numFields);
    }

}
