import org.junit.Test;
import static org.junit.Assert.*;

public class EEcuLgzNLATest {

    @Test
    public void testEvenNumber() {
        assertTrue(EEcuLgzNLA(2));
    }

    @Test
    public void testOddNumber() {
        assertFalse(EEcuLgzNLA(3));
    }

    private boolean EEcuLgzNLA(int JnIEDXNGMb) {
        return JnIEDXNGMb % 2 == 0;
    }
}
