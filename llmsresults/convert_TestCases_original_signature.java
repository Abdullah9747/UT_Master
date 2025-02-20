import org.junit.Test;
import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void testInvalidInputTooSmall() {
        Solution sol = new Solution();
        assertEquals("Invalid input. Number must be between 1 and 3999.", sol.convert(0));
    }

    @Test
    public void testInvalidInputTooLarge() {
        Solution sol = new Solution();
        assertEquals("Invalid input. Number must be between 1 and 3999.", sol.convert(4000));
    }

    @Test
    public void testOne() {
        Solution sol = new Solution();
        assertEquals("I", sol.convert(1));
    }

    @Test
    public void testThree() {
        Solution sol = new Solution();
        assertEquals("III", sol.convert(3));
    }

    @Test
    public void testFour() {
        Solution sol = new Solution();
        assertEquals("IV", sol.convert(4));
    }

    @Test
    public void testFive() {
        Solution sol = new Solution();
        assertEquals("V", sol.convert(5));
    }

    @Test
    public void testEight() {
        Solution sol = new Solution();
        assertEquals("VIII", sol.convert(8));
    }

    @Test
    public void testNine() {
        Solution sol = new Solution();
        assertEquals("IX", sol.convert(9));
    }

    @Test
    public void testTen() {
        Solution sol = new Solution();
        assertEquals("X", sol.convert(10));
    }

    @Test
    public void testThirteen() {
        Solution sol = new Solution();
        assertEquals("XIII", sol.convert(13));
    }

    @Test
    public void testFourteen() {
        Solution sol = new Solution();
        assertEquals("XIV", sol.convert(14));
    }

    @Test
    public void testFifteen() {
        Solution sol = new Solution();
        assertEquals("XV", sol.convert(15));
    }

    @Test
    public void testSixteen() {
        Solution sol = new Solution();
        assertEquals("XVI", sol.convert(16));
    }

    @Test
    public void testNineteen() {
        Solution sol = new Solution();
        assertEquals("XIX", sol.convert(19));
    }

    @Test
    public void testTwenty() {
        Solution sol = new Solution();
        assertEquals("XX", sol.convert(20));
    }

    @Test
    public void testThirtyNine() {
        Solution sol = new Solution();
        assertEquals("XXXIX", sol.convert(39));
    }

    @Test
    public void testForty() {
        Solution sol = new Solution();
        assertEquals("XL", sol.convert(40));
    }

    @Test
    public void testFortyNine() {
        Solution sol = new Solution();
        assertEquals("XLIX", sol.convert(49));
    }

    @Test
    public void testFifty() {
        Solution sol = new Solution();
        assertEquals("L", sol.convert(50));
    }

    @Test
    public void testEightyNine() {
        Solution sol = new Solution();
        assertEquals("LXXXIX", sol.convert(89));
    }

    @Test
    public void testNinety() {
        Solution sol = new Solution();
        assertEquals("XC", sol.convert(90));
    }

    @Test
    public void testNinetyNine() {
        Solution sol = new Solution();
        assertEquals("XCIX", sol.convert(99));
    }

    @Test
    public void testOneHundred() {
        Solution sol = new Solution();
        assertEquals("C", sol.convert(100));
    }

    @Test
    public void testFourHundred() {
        Solution sol = new Solution();
        assertEquals("CD", sol.convert(400));
    }

    @Test
    public void testFiveHundred() {
        Solution sol = new Solution();
        assertEquals("D", sol.convert(500));
    }

    @Test
    public void testNineHundred() {
        Solution sol = new Solution();
        assertEquals("CM", sol.convert(900));
    }

    @Test
    public void testOneThousand() {
        Solution sol = new Solution();
        assertEquals("M", sol.convert(1000));
    }

    @Test
    public void testOneThousandNineHundredEightyFour() {
        Solution sol = new Solution();
        assertEquals("MCMLXXXIV", sol.convert(1984));
    }

    @Test
    public void testTwoThousand() {
        Solution sol = new Solution();
        assertEquals("MM", sol.convert(2000));
    }

    @Test
    public void testThreeThousandNineHundredNinetyNine() {
        Solution sol = new Solution();
        assertEquals("MMMCMXCIX", sol.convert(3999));
    }

    @Test
    public void testTwoThousandFourHundredTwentyOne() {
        Solution sol = new Solution();
        assertEquals("MMCDXXI", sol.convert(2421));
    }
}
