import org.junit.Assert;
import org.junit.Test;

public class GETBLOCKTEXTUREFROMSIDEANDMETADATATest {

    @Test
    public void testCase1() {
        Assert.assertEquals(0, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(0, 0));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(2, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(1, 0));
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(3, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(2, 0));
    }
}