import org.junit.Assert;
import org.junit.Test;

public class ISSTATUSERRORTest {

    @Test
    public void testCase1() {
        Assert.assertEquals(false, ISSTATUSERROR.isStatusError(200));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(true, ISSTATUSERROR.isStatusError(400));
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(true, ISSTATUSERROR.isStatusError(500));
    }
}