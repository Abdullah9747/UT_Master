import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class DropPartitionTest {

    private Map<Integer, Object> perPartitionDayWriters = new HashMap<>();
    private Map<Integer, Long> perPartitionStartOffset = new HashMap<>();

    private static class DummyWriter {}

    public void dropPartition(int partitionId) {
        synchronized (perPartitionDayWriters) {
            perPartitionDayWriters.remove(partitionId);
            perPartitionStartOffset.remove(partitionId);
        }
    }

    @Test
    public void testBasicPartitionRemoval() {
        perPartitionDayWriters.put(1, new DummyWriter());
        perPartitionStartOffset.put(1, 100L);

        dropPartition(1);

        assertNull(perPartitionDayWriters.get(1));
        assertNull(perPartitionStartOffset.get(1));
    }

    @Test
    public void testPartitionDoesNotExist() {
        int initialWriterSize = perPartitionDayWriters.size();
        int initialOffsetSize = perPartitionStartOffset.size();

        dropPartition(2);

        assertEquals(initialWriterSize, perPartitionDayWriters.size());
        assertEquals(initialOffsetSize, perPartitionStartOffset.size());
    }

    @Test(expected = NullPointerException.class)
    public void testNullDataStructures() {
        perPartitionDayWriters = null;
        perPartitionStartOffset = null;

        dropPartition(3);
    }
}
