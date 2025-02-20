import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PartitionDropperTest {

    private PartitionDropper partitionDropper;
    private PartitionManager partitionManager;
    private DataStorage dataStorage;
    private IndexManager indexManager;
    private Logger logger;

    @Before
    public void setUp() {
        partitionManager = Mockito.mock(PartitionManager.class);
        dataStorage = Mockito.mock(DataStorage.class);
        indexManager = Mockito.mock(IndexManager.class);
        logger = Mockito.mock(Logger.class);
        partitionDropper = new PartitionDropper(partitionManager, dataStorage, indexManager);
    }

    @Test
    public void testPartitionDoesNotExist() {
        int partitionId = 1;
        when(partitionManager.partitionExists(partitionId)).thenReturn(false);

        partitionDropper.dropPartition(partitionId);

        verify(partitionManager, times(1)).partitionExists(partitionId);
        verify(dataStorage, never()).deleteDataForPartition(anyInt());
        verify(partitionManager, never()).removePartition(anyInt());
        verify(indexManager, never()).updateIndexesAfterPartitionDrop(anyInt());
    }

    @Test
    public void testDataDeletionFails() throws DataStorageException {
        int partitionId = 2;
        when(partitionManager.partitionExists(partitionId)).thenReturn(true);
        doThrow(new DataStorageException("Simulated data deletion error")).when(dataStorage).deleteDataForPartition(partitionId);

        partitionDropper.dropPartition(partitionId);

        verify(partitionManager, times(1)).partitionExists(partitionId);
        verify(dataStorage, times(1)).deleteDataForPartition(partitionId);
        verify(partitionManager, never()).removePartition(anyInt());
        verify(indexManager, never()).updateIndexesAfterPartitionDrop(anyInt());
    }

    @Test
    public void testPartitionRemovalFails() throws DataStorageException, PartitionManagementException {
        int partitionId = 3;
        when(partitionManager.partitionExists(partitionId)).thenReturn(true);
        doNothing().when(dataStorage).deleteDataForPartition(partitionId);
        doThrow(new PartitionManagementException("Simulated partition removal error")).when(partitionManager).removePartition(partitionId);

        partitionDropper.dropPartition(partitionId);

        verify(partitionManager, times(1)).partitionExists(partitionId);
        verify(dataStorage, times(1)).deleteDataForPartition(partitionId);
        verify(partitionManager, times(1)).removePartition(partitionId);
        verify(indexManager, never()).updateIndexesAfterPartitionDrop(anyInt());
    }

    @Test
    public void testIndexUpdateFails() throws DataStorageException, PartitionManagementException, IndexUpdateException {
        int partitionId = 4;
        when(partitionManager.partitionExists(partitionId)).thenReturn(true);
        doNothing().when(dataStorage).deleteDataForPartition(partitionId);
        doNothing().when(partitionManager).removePartition(partitionId);
        doThrow(new IndexUpdateException("Simulated index update error")).when(indexManager).updateIndexesAfterPartitionDrop(partitionId);

        partitionDropper.dropPartition(partitionId);

        verify(partitionManager, times(1)).partitionExists(partitionId);
        verify(dataStorage, times(1)).deleteDataForPartition(partitionId);
        verify(partitionManager, times(1)).removePartition(partitionId);
        verify(indexManager, times(1)).updateIndexesAfterPartitionDrop(partitionId);
    }

    @Test
    public void testSuccessfulPartitionDrop() throws DataStorageException, PartitionManagementException, IndexUpdateException {
        int partitionId = 5;
        when(partitionManager.partitionExists(partitionId)).thenReturn(true);
        doNothing().when(dataStorage).deleteDataForPartition(partitionId);
        doNothing().when(partitionManager).removePartition(partitionId);
        doNothing().when(indexManager).updateIndexesAfterPartitionDrop(partitionId);

        partitionDropper.dropPartition(partitionId);

        verify(partitionManager, times(1)).partitionExists(partitionId);
        verify(dataStorage, times(1)).deleteDataForPartition(partitionId);
        verify(partitionManager, times(1)).removePartition(partitionId);
        verify(indexManager, times(1)).updateIndexesAfterPartitionDrop(partitionId);
    }
}
