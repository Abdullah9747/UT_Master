import org.junit.Test;
import static org.junit.Assert.*;

public class OrdinalTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeInput() {
        Ordinal.kBMAaxcpby(-1);
    }

    @Test
    public void test11() {
        assertEquals("th", Ordinal.kBMAaxcpby(11));
    }

    @Test
    public void test12() {
        assertEquals("th", Ordinal.kBMAaxcpby(12));
    }

    @Test
    public void test13() {
        assertEquals("th", Ordinal.kBMAaxcpby(13));
    }

    @Test
    public void test1() {
        assertEquals("st", Ordinal.kBMAaxcpby(1));
    }

    @Test
    public void test2() {
        assertEquals("nd", Ordinal.kBMAaxcpby(2));
    }

    @Test
    public void test3() {
        assertEquals("rd", Ordinal.kBMAaxcpby(3));
    }

    @Test
    public void test4() {
        assertEquals("th", Ordinal.kBMAaxcpby(4));
    }

    @Test
    public void test101() {
        assertEquals("st", Ordinal.kBMAaxcpby(101));
    }

    @Test
    public void test102() {
        assertEquals("nd", Ordinal.kBMAaxcpby(102));
    }

    @Test
    public void test103() {
        assertEquals("rd", Ordinal.kBMAaxcpby(103));
    }

    @Test
    public void test104() {
        assertEquals("th", Ordinal.kBMAaxcpby(104));
    }
}
