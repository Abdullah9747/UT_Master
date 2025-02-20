import org.junit.Test;
import static org.junit.Assert.*;

public class ColumnTest {

    @Test
    public void testSetColspan_ValidValue() {
        Column column = new Column();
        Column result = column.setColspan(2);
        assertEquals(2, column.colspan);
        assertEquals(column, result);
    }

    @Test
    public void testSetColspan_NegativeValue() {
        Column column = new Column();
        Column result = column.setColspan(-1);
        assertEquals(1, column.colspan);
        assertEquals(column, result);
    }

    @Test
    public void testSetColspan_ZeroValue() {
        Column column = new Column();
        Column result = column.setColspan(0);
        assertEquals(1, column.colspan);
        assertEquals(column, result);
    }

    @Test
    public void testSetColspan_TooLargeValue() {
        Column column = new Column();
        int MAX_COLSPAN = 10;
        Column result = column.setColspan(100);
        assertEquals(MAX_COLSPAN, column.colspan);
        assertEquals(column, result);
    }

    @Test
    public void testSetColspan_MinimumValidValue() {
        Column column = new Column();
        Column result = column.setColspan(1);
        assertEquals(1, column.colspan);
        assertEquals(column, result);
    }

    @Test
    public void testSetColspan_MaximumValidValue() {
        Column column = new Column();
        int MAX_COLSPAN = 10;
        Column result = column.setColspan(MAX_COLSPAN - 1);
        assertEquals(MAX_COLSPAN - 1, column.colspan);
        assertEquals(column, result);
    }

    private class Column {
        public int colspan;

        public Column setColspan(int colspan) {
            this.colspan = checkColspan(colspan);
            return this;
        }

        private int checkColspan(int colspan) {
            int MAX_COLSPAN = 10;
            if (colspan <= 0) {
                return 1;
            } else if (colspan > MAX_COLSPAN) {
                return MAX_COLSPAN;
            } else {
                return colspan;
            }
        }
    }
}
