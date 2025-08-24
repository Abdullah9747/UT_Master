import org.junit.Assert;
import org.junit.Test;

public class SHORTTOULAWTest {

    @Test
    public void testCase1() {
        Assert.assertEquals(128, SHORTTOULAW.shortToUlaw((short) 0));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(127, SHORTTOULAW.shortToUlaw((short) -1));
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(255, SHORTTOULAW.shortToUlaw((short) 32767));
    }

    @Test
    public void testCase4() {
        Assert.assertEquals(0, SHORTTOULAW.shortToUlaw((short) -32768));
    }

    @Test
    public void testCase5() {
        Assert.assertEquals(255, SHORTTOULAW.shortToUlaw((short) 16384));
        Assert.assertEquals(0, SHORTTOULAW.shortToUlaw((short) -16384));
    }
}