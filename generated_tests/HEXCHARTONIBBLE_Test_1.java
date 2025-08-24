import org.junit.Assert;
import org.junit.Test;

public class HEXCHARTONIBBLETest {

    @Test
    public void testCase1() {
        Assert.assertEquals(0, HEXCHARTONIBBLE.hexCharToNibble('0'));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(9, HEXCHARTONIBBLE.hexCharToNibble('9'));
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(10, HEXCHARTONIBBLE.hexCharToNibble('a'));
    }

    @Test
    public void testCase4() {
        Assert.assertEquals(15, HEXCHARTONIBBLE.hexCharToNibble('f'));
    }

    @Test
    public void testCase5() {
        Assert.assertEquals(10, HEXCHARTONIBBLE.hexCharToNibble('A'));
    }

    @Test
    public void testCase6() {
        Assert.assertEquals(15, HEXCHARTONIBBLE.hexCharToNibble('F'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase7() {
        HEXCHARTONIBBLE.hexCharToNibble('g');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase8() {
        HEXCHARTONIBBLE.hexCharToNibble('G');
    }
}