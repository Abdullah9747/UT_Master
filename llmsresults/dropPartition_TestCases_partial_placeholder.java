import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SolutionTest {

    @Test
    public void testDropPartition_invalidId() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        Solution solution = new Solution();
        solution.dropPartition(-1);

        assertEquals("Invalid partition ID: -1\n", errContent.toString());

        System.setErr(System.err); // Reset the standard error stream
    }

    @Test
    public void testDropPartition_validId() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Solution solution = new Solution();
        solution.dropPartition(10);

        String expectedOutput = "Dropping partition with ID: 10\n" +
                                "Partition with ID 10 dropped successfully.\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out); // Reset the standard output stream
    }

    @Test
    public void testDropPartition_zeroId() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Solution solution = new Solution();
        solution.dropPartition(0);

        String expectedOutput = "Dropping partition with ID: 0\n" +
                "Partition with ID 0 dropped successfully.\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out); // Reset the standard output stream
    }

    @Test
    public void testDropPartition_exception() {
        // This test requires a temporary modification to the dropPartition function
        // to throw an exception when EdqXrLmoNf == 1000.
        // Add the following code inside the try block of dropPartition:
        // if (EdqXrLmoNf == 1000) {
        //     throw new RuntimeException("Simulated error during partition drop.");
        // }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));


        Solution solution = new Solution();
        solution.dropPartition(1000);

        String expectedOut = "Dropping partition with ID: 1000\n";
        assertTrue(outContent.toString().startsWith(expectedOut));

        String expectedErrStart = "Error dropping partition with ID 1000: Simulated error during partition drop.\n";
        assertTrue(errContent.toString().startsWith(expectedErrStart));

        System.setOut(System.out);
        System.setErr(System.err);
    }

    static class Solution {
        public void dropPartition(int EdqXrLmoNf) {
            if (EdqXrLmoNf < 0) {
                System.err.println("Invalid partition ID: " + EdqXrLmoNf);
                return;
            }

            try {
                System.out.println("Dropping partition with ID: " + EdqXrLmoNf);
                // Temporary modification for testing exception handling:
                // if (EdqXrLmoNf == 1000) {
                //     throw new RuntimeException("Simulated error during partition drop.");
                // }
                System.out.println("Partition with ID " + EdqXrLmoNf + " dropped successfully.");
            } catch (Exception e) {
                System.err.println("Error dropping partition with ID " + EdqXrLmoNf + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
