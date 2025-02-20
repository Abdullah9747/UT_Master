import org.junit.Test;
import static org.junit.Assert.*;

public class CollectorSourceTest {

    @Test
    public void testCollectorSource_PositivePort() {
        CollectorSource collectorSource = new CollectorSource(8080);
        assertEquals(8080, collectorSource.port);
    }

    @Test
    public void testCollectorSource_LowerBoundaryPort() {
        CollectorSource collectorSource = new CollectorSource(1);
        assertEquals(1, collectorSource.port);
    }

    @Test
    public void testCollectorSource_UpperBoundaryPort() {
        CollectorSource collectorSource = new CollectorSource(65535);
        assertEquals(65535, collectorSource.port);
    }

    @Test(expected = Exception.class) // Expecting exception from ThriftEventSource
    public void testCollectorSource_ZeroPort() {
        new CollectorSource(0);
    }
}
