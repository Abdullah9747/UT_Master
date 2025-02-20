import org.junit.Test;
import static org.junit.Assert.*;

public class FieldManagerTest {

    @Test
    public void testRemoveField_ArrayList_ValidIndex() {
        // Implementation using ArrayList
        FieldManagerArrayList fm = new FieldManagerArrayList();
        fm.addField("Field1");
        fm.addField("Field2");
        fm.addField("Field3");
        fm.removeField(1); // Remove "Field2" at index 1
        assertEquals(2, fm.getFields().size());
        assertEquals("Field1", fm.getFields().get(0));
        assertEquals("Field3", fm.getFields().get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveField_ArrayList_InvalidIndex_Negative() {
        FieldManagerArrayList fm = new FieldManagerArrayList();
        fm.addField("Field1");
        fm.removeField(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveField_ArrayList_InvalidIndex_TooLarge() {
        FieldManagerArrayList fm = new FieldManagerArrayList();
        fm.addField("Field1");
        fm.removeField(1);
    }

    @Test
    public void testRemoveField_HashMap_ValidKey() {
        // Implementation using HashMap
        FieldManagerHashMap fm = new FieldManagerHashMap();
        fm.addField(1, "Field1");
        fm.addField(2, "Field2");
        fm.addField(3, "Field3");
        fm.removeField(2); // Remove "Field2" with key 2
        assertEquals(2, fm.getFields().size());
        assertFalse(fm.getFields().containsKey(2));
    }

    @Test
    public void testRemoveField_HashMap_InvalidKey() {
        FieldManagerHashMap fm = new FieldManagerHashMap();
        fm.addField(1, "Field1");
        fm.removeField(2); // Remove non-existent key
        assertEquals(1, fm.getFields().size());
    }

    @Test
    public void testRemoveField_Array_ValidIndex() {
        // Implementation using Array (with nulling)
        FieldManagerArray fm = new FieldManagerArray(3);
        fm.addField("Field1", 0);
        fm.addField("Field2", 1);
        fm.addField("Field3", 2);
        fm.removeField(1); // Remove "Field2" at index 1
        assertNull(fm.getFields()[1]);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testRemoveField_Array_InvalidIndex_Negative() {
        FieldManagerArray fm = new FieldManagerArray(3);
        fm.removeField(-1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testRemoveField_Array_InvalidIndex_TooLarge() {
        FieldManagerArray fm = new FieldManagerArray(3);
        fm.removeField(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveField_WithException_InvalidIndex() {
        FieldManagerWithException fm = new FieldManagerWithException();
        fm.addField("Field1");
        fm.removeField(-1);
    }

    @Test
    public void testRemoveField_FirstElement() {
        FieldManagerArrayList fm = new FieldManagerArrayList();
        fm.addField("Field1");
        fm.addField("Field2");
        fm.removeField(0);
        assertEquals(1, fm.getFields().size());
        assertEquals("Field2", fm.getFields().get(0));
    }

    @Test
    public void testRemoveField_LastElement() {
        FieldManagerArrayList fm = new FieldManagerArrayList();
        fm.addField("Field1");
        fm.addField("Field2");
        fm.removeField(1);
        assertEquals(1, fm.getFields().size());
        assertEquals("Field1", fm.getFields().get(0));
    }

    // Dummy FieldManager implementations for testing purposes
    static class FieldManagerArrayList {
        private java.util.ArrayList<String> fields = new java.util.ArrayList<>();

        public void addField(String field) {
            fields.add(field);
        }

        public void removeField(int fieldNum) {
            fields.remove(fieldNum);
        }

        public java.util.ArrayList<String> getFields() {
            return fields;
        }
    }

    static class FieldManagerHashMap {
        private java.util.HashMap<Integer, String> fields = new java.util.HashMap<>();
        private int nextKey = 1;

        public void addField(int key, String field) {
            fields.put(key, field);
        }

        public void removeField(int fieldNum) {
            fields.remove(fieldNum);
        }

        public java.util.HashMap<Integer, String> getFields() {
            return fields;
        }
    }

    static class FieldManagerArray {
        private String[] fields;

        public FieldManagerArray(int size) {
            fields = new String[size];
        }

        public void addField(String field, int index) {
            fields[index] = field;
        }

        public void removeField(int fieldNum) {
            fields[fieldNum] = null;
        }

        public String[] getFields() {
            return fields;
        }
    }

    static class FieldManagerWithException {
        private java.util.ArrayList<String> fields = new java.util.ArrayList<>();

        public void addField(String field) {
            fields.add(field);
        }

        public void removeField(int fieldNum) {
            if (fieldNum < 0 || fieldNum >= fields.size()) {
                throw new IllegalArgumentException("Invalid field number: " + fieldNum);
            }
            fields.remove(fieldNum);
        }
    }
}
