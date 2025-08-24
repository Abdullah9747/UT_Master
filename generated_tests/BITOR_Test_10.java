import org.junit.Assert;
import org.junit.Test;

public class BITORTest {

    @Test
    public void testCase1() {
        Assert.assertEquals(0, BITOR.BITOR(0, 0));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(1, BITOR.BITOR(0, 1));
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(1, BITOR.BITOR(1, 0));
    }

    @Test
    public void testCase4() {
        Assert.assertEquals(-1, BITOR.BITOR(-1, -1));
    }
}