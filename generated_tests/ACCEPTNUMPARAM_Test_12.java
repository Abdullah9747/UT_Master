import org.junit.Assert;
import org.junit.Test;

public class ACCEPTNUMPARAMTest {

    @Test
    public void testCase1() {
        Assert.assertEquals(true, new ACCEPTNUMPARAM().acceptNumParam(2));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(false, new ACCEPTNUMPARAM().acceptNumParam(1));
    }
}