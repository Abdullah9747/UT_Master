import org.junit.Test;
import static org.junit.Assert.*;

public class OrdinalOfTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput() {
        OrdinalConverter.ordinalOf(-1);
    }

    @Test
    public void test1() {
        assertEquals("1st", OrdinalConverter.ordinalOf(1));
    }

    @Test
    public void test2() {
        assertEquals("2nd", OrdinalConverter.ordinalOf(2));
    }

    @Test
    public void test3() {
        assertEquals("3rd", OrdinalConverter.ordinalOf(3));
    }

    @Test
    public void test4() {
        assertEquals("4th", OrdinalConverter.ordinalOf(4));
    }

    @Test
    public void test11() {
        assertEquals("11th", OrdinalConverter.ordinalOf(11));
    }

    @Test
    public void test12() {
        assertEquals("12th", OrdinalConverter.ordinalOf(12));
    }

    @Test
    public void test13() {
        assertEquals("13th", OrdinalConverter.ordinalOf(13));
    }

    @Test
    public void test21() {
        assertEquals("21st", OrdinalConverter.ordinalOf(21));
    }

    @Test
    public void test101() {
        assertEquals("101st", OrdinalConverter.ordinalOf(101));
    }

    @Test
    public void test111() {
        assertEquals("111th", OrdinalConverter.ordinalOf(111));
    }

    @Test
    public void test20() {
        assertEquals("20th", OrdinalConverter.ordinalOf(20));
    }

    @Test
    public void test100() {
        assertEquals("100th", OrdinalConverter.ordinalOf(100));
    }
    @Test
    public void test22() {
        assertEquals("22nd", OrdinalConverter.ordinalOf(22));
    }

    @Test
    public void test102() {
        assertEquals("102nd", OrdinalConverter.ordinalOf(102));
    }

    @Test
    public void test112() {
        assertEquals("112th", OrdinalConverter.ordinalOf(112));
    }
    @Test
    public void test23() {
        assertEquals("23rd", OrdinalConverter.ordinalOf(23));
    }

    @Test
    public void test103() {
        assertEquals("103rd", OrdinalConverter.ordinalOf(103));
    }

    @Test
    public void test113() {
        assertEquals("113th", OrdinalConverter.ordinalOf(113));
    }

    @Test
    public void test14() {
        assertEquals("14th", OrdinalConverter.ordinalOf(14));
    }

}

class OrdinalConverter {
    public static String ordinalOf(int oneBased) {
        if (oneBased <= 0) {
            throw new IllegalArgumentException("Input must be a positive integer.");
        }

        int mod100 = oneBased % 100;
        if (mod100 >= 11 && mod100 <= 13) {
            return oneBased + "th";
        }

        switch (oneBased % 10) {
            case 1:  return oneBased + "st";
            case 2:  return oneBased + "nd";
            case 3:  return oneBased + "rd";
            default: return oneBased + "th";
        }
    }
}
