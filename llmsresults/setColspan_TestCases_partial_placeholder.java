import org.junit.Test;
import static org.junit.Assert.*;

public class ColumnTest {

    private static final int MAX_COLSPAN = 10;

    @Test
    public void testSetColspan_ValidValue() {
        Column column = new Column();
        Column returnedColumn = column.setColspan(3);
        assertEquals(3, column.getColspan());
        assertEquals(column, returnedColumn);
    }

    @Test
    public void testSetColspan_MinimumValue() {
        Column column = new Column();
        Column returnedColumn = column.setColspan(1);
        assertEquals(1, column.getColspan());
        assertEquals(column, returnedColumn);
    }

    @Test
    public void testSetColspan_MaximumValue() {
        Column column = new Column();
        Column returnedColumn = column.setColspan(MAX_COLSPAN);
        assertEquals(MAX_COLSPAN, column.getColspan());
        assertEquals(column, returnedColumn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColspan_InvalidValueZero() {
        Column column = new Column();
        column.setColspan(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColspan_InvalidValueNegative() {
        Column column = new Column();
        column.setColspan(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColspan_InvalidValueTooLarge() {
        Column column = new Column();
        column.setColspan(MAX_COLSPAN + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColspan_MaxValue() {
        Column column = new Column();
        column.setColspan(Integer.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColspan_FloatMaxValue() {
        Column column = new Column();
        column.setColspan((int) (Float.MAX_VALUE));
    }

    // Assuming you have a getColspan() method in your Column class
    private class Column {
        private int colspan;

        public Column setColspan(int colspan) {
            if (colspan < 1 || colspan > MAX_COLSPAN) {
                throw new IllegalArgumentException("Invalid colspan value: " + colspan);
            }
            this.colspan = colspan;
            return this;
        }

        public int getColspan() {
            return colspan;
        }
    }
}
