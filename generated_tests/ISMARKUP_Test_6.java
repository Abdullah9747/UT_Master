import org.junit.Assert;
import org.junit.Test;

public class ISMARKUPTest {

    @Test
    public void testCase1() {
        Assert.assertEquals(false, ISMARKUP.isMarkup(60));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(true, ISMARKUP.isMarkup(37));
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(true, ISMARKUP.isMarkup(38));
    }

    @Test
    public void testCase4() {
        Assert.assertEquals(false, ISMARKUP.isMarkup(99));
    }
}