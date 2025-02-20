import org.junit.Test;
import static org.junit.Assert.*;

public class CollectorSourceTest {

    @Test
    public void testConstructorWithZeroPort() {
        CollectorSource source = new CollectorSource(0);
        assertEquals(0, source.getPort());
    }

    @Test
    public void testConstructorWithOnePort() {
        CollectorSource source = new CollectorSource(1);
        assertEquals(1, source.getPort());
    }

    @Test
    public void testConstructorWith1023Port() {
        CollectorSource source = new CollectorSource(1023);
        assertEquals(1023, source.getPort());
    }

    @Test
    public void testConstructorWith1024Port() {
        CollectorSource source = new CollectorSource(1024);
        assertEquals(1024, source.getPort());
    }

    @Test
    public void testConstructorWithMaxPort() {
        CollectorSource source = new CollectorSource(65535);
        assertEquals(65535, source.getPort());
    }

    @Test
    public void testConstructorWithNegativePort() {
        CollectorSource source = new CollectorSource(-1);
        assertEquals(-1, source.getPort());
    }

    @Test
    public void testConstructorWithTooLargePort() {
        CollectorSource source = new CollectorSource(65536);
        assertEquals(65536, source.getPort());
    }
}
