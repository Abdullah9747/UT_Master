import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.TimeZone;

public class ExpireAtMonthTest {

    @Test
    public void testCase1() {
        long expectedTimestamp = calculateExpectedTimestamp(2024, 6, 15, 10);
        long actualTimestamp = expireAtMonth(2024, 6, 15, 10);
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void testCase2() {
        long expectedTimestamp = calculateExpectedTimestamp(2023, 1, 1, 0);
        long actualTimestamp = expireAtMonth(2023, 1, 1, 0);
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void testCase3() {
        long expectedTimestamp = calculateExpectedTimestamp(2025, 12, 31, 23);
        long actualTimestamp = expireAtMonth(2025, 12, 31, 23);
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void testCase4() {
        long expectedTimestamp = calculateExpectedTimestamp(2024, 2, 29, 12);
        long actualTimestamp = expireAtMonth(2024, 2, 29, 12);
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void testCase5() {
        long expectedTimestamp = calculateExpectedTimestamp(2023, 4, 30, 6);
        long actualTimestamp = expireAtMonth(2023, 4, 30, 6);
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void testCase6() {
        long expectedTimestamp = calculateExpectedTimestamp(1970, 1, 1, 0);
        long actualTimestamp = expireAtMonth(1970, 1, 1, 0);
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void testCase7() {
        long expectedTimestamp = calculateExpectedTimestamp(2024, 7, 1, 0);
        long actualTimestamp = expireAtMonth(2024, 7, 1, 0);
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void testCase8() {
        long expectedTimestamp = calculateExpectedTimestamp(2024, 7, 1, 23);
        long actualTimestamp = expireAtMonth(2024, 7, 1, 23);
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    private long calculateExpectedTimestamp(int year, int month, int day, int hour) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(year, month - 1, day, hour, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    long expireAtMonth(int DUNrxpGdNO, int OfBEdHcksC, int CxevAVbIoL, int DBWvzPlKac) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(DUNrxpGdNO, OfBEdHcksC - 1, CxevAVbIoL, DBWvzPlKac, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long timestamp = calendar.getTimeInMillis();
        return timestamp;
    }
}
