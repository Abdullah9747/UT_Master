import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static org.junit.Assert.*;

public class FileMappingTest {

    private RandomAccessFile raf;
    private File testFile;

    // Assume the function to be tested is in a class called FileMapper
    private static class FileMapper {
        private RandomAccessFile raf;

        public FileMapper() {
            this.raf = null;
        }

        public MappedByteBuffer mapFile(File file, long size) throws IOException {
            if (raf == null) {
                raf = new RandomAccessFile(file, "rw");
            }
            return raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, size);
        }

        public void closeRaf() throws IOException {
            if (raf != null) {
                raf.close();
            }
        }
    }

    private FileMapper fileMapper;

    @Before
    public void setUp() throws IOException {
        testFile = new File("test.txt");
        if (!testFile.exists()) {
            testFile.createNewFile();
        }
        fileMapper = new FileMapper();
    }

    @After
    public void tearDown() throws IOException {
        if (testFile.exists()) {
            testFile.delete();
        }
        if (fileMapper != null) {
            fileMapper.closeRaf();
        }
    }

    @Test
    public void testMapFile_rafIsNull() throws IOException {
        MappedByteBuffer buffer = fileMapper.mapFile(testFile, 1024);
        assertNotNull(buffer);
        // The raf should not be null after the first call.
        try {
            fileMapper.closeRaf();
        } catch (IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }

    }

    @Test
    public void testMapFile_rafIsNotNull() throws IOException {

        // Initialize raf before calling the function
        RandomAccessFile initialRaf = new RandomAccessFile(testFile, "rw");
        fileMapper.raf = initialRaf;

        MappedByteBuffer buffer = fileMapper.mapFile(testFile, 2048);
        assertNotNull(buffer);

        // Check raf is the same instance.
        try {
            fileMapper.closeRaf();
        } catch (IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }

    }

    @Test(expected = IOException.class)
    public void testMapFile_fileNotFound() throws IOException {
        File nonExistentFile = new File("nonexistent.txt");
        fileMapper.mapFile(nonExistentFile, 1024);
    }

    @Test
    public void testMapFile_sizeGreaterThanFileSize() throws IOException {
        // Create a small file
        testFile.delete();
        testFile.createNewFile();

        try (RandomAccessFile raf = new RandomAccessFile(testFile, "rw")) {
            raf.setLength(100); // Create a file with size 100 bytes
        }

        MappedByteBuffer buffer = fileMapper.mapFile(testFile, 99999);
        assertNotNull(buffer);
        assertTrue(buffer.capacity() <= 99999);  // Ensure that the buffer size is not larger than requested.
        assertTrue(buffer.capacity() <= 100); // Ensure that buffer is not larger than file size
    }
}
