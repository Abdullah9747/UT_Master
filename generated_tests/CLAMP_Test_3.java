import org.junit.Assert;
import org.junit.Test;

public class CLAMPTest {

    @Test
    public void testCase1() {
        Assert.assertEquals(2.0f, CLAMP.clamp(1.0f, 2.0f, 3.0f), 0.0f);
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(3.0f, CLAMP.clamp(4.0f, 5.0f, 3.0f), 0.0f);
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(4.0f, CLAMP.clamp(4.0f, 3.0f, 5.0f), 0.0f);
    }
}