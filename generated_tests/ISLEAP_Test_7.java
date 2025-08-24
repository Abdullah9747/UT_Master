import org.junit.Assert;
import org.junit.Test;

public class ISLEAPTest {

    @Test
    public void testCase1() {
        Assert.assertEquals(false, new ISLEAP().isLeap(0));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(false, new ISLEAP().isLeap(1));
    }
}