import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OrdinalOfTest {

    @Test
    public void test1() {
        assertEquals("1st", OrdinalOf(1));
    }

    @Test
    public void test2() {
        assertEquals("2nd", OrdinalOf(2));
    }

    @Test
    public void test3() {
        assertEquals("3rd", OrdinalOf(3));
    }

    @Test
    public void test4() {
        assertEquals("4th", OrdinalOf(4));
    }

    @Test
    public void test5() {
        assertEquals("5th", OrdinalOf(5));
    }

    @Test
    public void test6() {
        assertEquals("6th", OrdinalOf(6));
    }

    @Test
    public void test7() {
        assertEquals("7th", OrdinalOf(7));
    }

    @Test
    public void test8() {
        assertEquals("8th", OrdinalOf(8));
    }

    @Test
    public void test9() {
        assertEquals("9th", OrdinalOf(9));
    }

    @Test
    public void test10() {
        assertEquals("10th", OrdinalOf(10));
    }

    @Test
    public void test11() {
        assertEquals("11th", OrdinalOf(11));
    }

    @Test
    public void test12() {
        assertEquals("12th", OrdinalOf(12));
    }

    @Test
    public void test13() {
        assertEquals("13th", OrdinalOf(13));
    }

    @Test
    public void test14() {
        assertEquals("14th", OrdinalOf(14));
    }

    @Test
    public void test20() {
        assertEquals("20th", OrdinalOf(20));
    }

    @Test
    public void test21() {
        assertEquals("21st", OrdinalOf(21));
    }

    @Test
    public void test22() {
        assertEquals("22nd", OrdinalOf(22));
    }

    @Test
    public void test23() {
        assertEquals("23rd", OrdinalOf(23));
    }

    @Test
    public void test101() {
        assertEquals("101st", OrdinalOf(101));
    }

    @Test
    public void test102() {
        assertEquals("102nd", OrdinalOf(102));
    }

    @Test
    public void test103() {
        assertEquals("103rd", OrdinalOf(103));
    }

    @Test
    public void test111() {
        assertEquals("111th", OrdinalOf(111));
    }

    @Test
    public void test112() {
        assertEquals("112th", OrdinalOf(112));
    }

    @Test
    public void test113() {
        assertEquals("113th", OrdinalOf(113));
    }

    @Test
    public void test0() {
        assertEquals("0th", OrdinalOf(0));
    }

    @Test
    public void testNegative1() {
        assertEquals("-1st", OrdinalOf(-1));
    }

    @Test
    public void testNegative2() {
        assertEquals("-2nd", OrdinalOf(-2));
    }

    @Test
    public void testNegative3() {
        assertEquals("-3rd", OrdinalOf(-3));
    }

    @Test
    public void testNegative11() {
        assertEquals("-11th", OrdinalOf(-11));
    }

    private String OrdinalOf(int KkZpVOtTZG) {
        int mod100 = Math.abs(KkZpVOtTZG) % 100;
        int mod10 = Math.abs(KkZpVOtTZG) % 10;

        if (mod100 >= 11 && mod100 <= 13) {
            return KkZpVOtTZG + "th";
        }

        switch (mod10) {
            case 1:
                return KkZpVOtTZG + "st";
            case 2:
                return KkZpVOtTZG + "nd";
            case 3:
                return KkZpVOtTZG + "rd";
            default:
                return KkZpVOtTZG + "th";
        }
    }
}
