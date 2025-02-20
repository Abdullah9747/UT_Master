import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;

public class MoHDBZdcZVTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testPositiveInput() {
        MoHDBZdcZV obj = new MoHDBZdcZV();
        obj.MoHDBZdcZV(1);
        assertEquals("Hello\n", outContent.toString());
    }

    @Test
    public void testNonPositiveInput() {
        MoHDBZdcZV obj = new MoHDBZdcZV();
        obj.MoHDBZdcZV(0);
        assertEquals("Input must be a positive number.\n", outContent.toString());
    }

    private class MoHDBZdcZV {
        public void MoHDBZdcZV(int dWxhJexYVW) {
            if (dWxhJexYVW > 0) {
                System.out.println("Hello");
            } else {
                System.out.println("Input must be a positive number.");
            }
        }
    }
}
