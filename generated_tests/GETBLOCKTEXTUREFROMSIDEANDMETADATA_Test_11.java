import org.junit.Assert;
import org.junit.Test;

public class GETBLOCKTEXTUREFROMSIDEANDMETADATATest {

    @Test
    public void testCase1() {
        Assert.assertEquals(54, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(0, 0));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(54, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(0, 1));
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(54, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(0, 2));
    }

    @Test
    public void testCase4() {
        Assert.assertEquals(54, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(0, 3));
    }

    @Test
    public void testCase5() {
        Assert.assertEquals(54, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(0, 4));
    }

    @Test
    public void testCase6() {
        Assert.assertEquals(54, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(0, 5));
    }

    @Test
    public void testCase7() {
        Assert.assertEquals(54, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(0, 10));
    }

    @Test
    public void testCase8() {
        Assert.assertEquals(54, GETBLOCKTEXTUREFROMSIDEANDMETADATA.getBlockTextureFromSideAndMetadata(1, 0));
    }
}