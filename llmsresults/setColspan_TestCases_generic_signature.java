import org.junit.Test;
import static org.junit.Assert.*;

public class PmSebArfdITest {

    @Test
    public void testPmSebArfdI() {
        Column column = PmSebArfdI(10);
        assertNotNull(column);
        assertEquals(10, column.getColumnSize());
    }

    private static class Column {
        private int columnSize;

        public int getColumnSize() {
            return columnSize;
        }

        public void setColumnSize(int columnSize) {
            this.columnSize = columnSize;
        }
    }

    private Column PmSebArfdI(int YCRfkgtcXU) {
        Column column = new Column();
        column.setColumnSize(YCRfkgtcXU);
        return column;
    }
}
