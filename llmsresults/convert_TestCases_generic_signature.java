import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ASibKkCConTest {

    @Test
    public void testASibKkCCon_0() {
        ASibKkCCon aSibKkCConInstance = new ASibKkCCon();
        assertEquals("", aSibKkCConInstance.aSibKkCCon(0));
    }

    @Test
    public void testASibKkCCon_1() {
        ASibKkCCon aSibKkCConInstance = new ASibKkCCon();
        assertEquals("1", aSibKkCConInstance.aSibKkCCon(1));
    }

    @Test
    public void testASibKkCCon_5() {
        ASibKkCCon aSibKkCConInstance = new ASibKkCCon();
        assertEquals("12345", aSibKkCConInstance.aSibKkCCon(5));
    }
}
