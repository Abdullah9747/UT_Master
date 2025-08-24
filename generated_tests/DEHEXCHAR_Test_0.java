import org.junit.Assert;
import org.junit.Test;

public class DEHEXCHARTEST {
    @Test
    public void testCase1() {
        Assert.assertEquals(0, DEHEXCHAR.dehexchar('0'));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(15, DEHEXCHAR.dehexchar('F'));
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(10, DEHEXCHAR.dehexchar('a'));
    }

    @Test
    public void testCase4() {
        Assert.assertEquals(-1, DEHEXCHAR.dehexchar('g'));
    }
}