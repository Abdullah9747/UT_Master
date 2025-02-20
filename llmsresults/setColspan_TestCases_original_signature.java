import org.junit.Test;
import static org.junit.Assert.*;

public class ColumnTest {

    @Test
    public void testSetColspan_Basic() {
        Column column = new Column();
        Column returnedColumn = column.setColspan(1);
        assertEquals(1, column.getColspan());
        assertSame(column, returnedColumn);
    }

    @Test
    public void testSetColspan_Zero() {
        Column column = new Column();
        Column returnedColumn = column.setColspan(0);
        assertEquals(0, column.getColspan());
        assertSame(column, returnedColumn);
    }

    @Test
    public void testSetColspan_Negative() {
        Column column = new Column();
        Column returnedColumn = column.setColspan(-1);
        assertEquals(-1, column.getColspan());
        assertSame(column, returnedColumn);
    }

    @Test
    public void testSetColspan_Large() {
        Column column = new Column();
        Column returnedColumn = column.setColspan(10);
        assertEquals(10, column.getColspan());
        assertSame(column, returnedColumn);
    }

    @Test
    public void testSetColspan_MaxValue() {
        Column column = new Column();
        Column returnedColumn = column.setColspan(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, column.getColspan());
        assertSame(column, returnedColumn);
    }

    static class Column {
        private int colspan;

        public Column setColspan(int colspan) {
            this.colspan = colspan;
            return this;
        }

        public int getColspan() {
            return colspan;
        }
    }
}
