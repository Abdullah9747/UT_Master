import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.TimeZone;

public class ExpirationTest {

    @Test
    public void testFutureDate() {
        ExpirationCalculator calc = new ExpirationCalculator();
        int addMonth = 1;
        int atDay = 1;
        int atHour = 1;
        int atMin = 1;

        long expectedTimeDifference = calculateExpectedTimeDifference(addMonth, atDay, atHour, atMin);
        long actualTimeDifference = calc.expireAtMonth(addMonth, atDay, atHour, atMin);

        assertEquals(expectedTimeDifference, actualTimeDifference, 2);
    }

    @Test
    public void testPastDate() {
        ExpirationCalculator calc = new ExpirationCalculator();
        int addMonth = -1;
        int atDay = 15;
        int atHour = 12;
        int atMin = 0;

        long expectedTimeDifference = calculateExpectedTimeDifference(addMonth, atDay, atHour, atMin);
        long actualTimeDifference = calc.expireAtMonth(addMonth, atDay, atHour, atMin);

        assertEquals(expectedTimeDifference, actualTimeDifference, 2);
    }

    @Test
    public void testZeroMonth() {
        ExpirationCalculator calc = new ExpirationCalculator();
        int addMonth = 0;
        int atDay = 1;
        int atHour = 1;
        int atMin = 1;

        long expectedTimeDifference = calculateExpectedTimeDifference(addMonth, atDay, atHour, atMin);
        long actualTimeDifference = calc.expireAtMonth(addMonth, atDay, atHour, atMin);

        assertEquals(expectedTimeDifference, actualTimeDifference, 2);
    }

    @Test
    public void testRealisticFuture() {
        ExpirationCalculator calc = new ExpirationCalculator();
        int addMonth = 2;
        int atDay = 28;
        int atHour = 15;
        int atMin = 30;

        long expectedTimeDifference = calculateExpectedTimeDifference(addMonth, atDay, atHour, atMin);
        long actualTimeDifference = calc.expireAtMonth(addMonth, atDay, atHour, atMin);

        assertEquals(expectedTimeDifference, actualTimeDifference, 2);
    }

    @Test
    public void testFutureToday() {
        ExpirationCalculator calc = new ExpirationCalculator();
        int addMonth = 0;
        int atDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int atHour = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1) % 24; // Add one hour
        int atMin = 0;

        long expectedTimeDifference = calculateExpectedTimeDifference(addMonth, atDay, atHour, atMin);
        long actualTimeDifference = calc.expireAtMonth(addMonth, atDay, atHour, atMin);

        assertEquals(expectedTimeDifference, actualTimeDifference, 2);
    }

    private long calculateExpectedTimeDifference(int addMonth, int atDay, int atHour, int atMin) {
        Calendar now = Calendar.getInstance();
        Calendar future = Calendar.getInstance();
        future.add(Calendar.MONTH, addMonth);
        future.set(Calendar.DAY_OF_MONTH, atDay);
        future.set(Calendar.HOUR_OF_DAY, atHour);
        future.set(Calendar.MINUTE, atMin);
        future.set(Calendar.SECOND, 0);
        future.set(Calendar.MILLISECOND, 0);

        long expectedMillis = future.getTimeInMillis() - now.getTimeInMillis();
        return expectedMillis / 1000;
    }

    static class ExpirationCalculator {
        public long expireAtMonth(int addMonth, int atDay, int atHour, int atMin) {
            long curTime = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, addMonth);
            calendar.set(Calendar.DAY_OF_MONTH, atDay);
            calendar.set(Calendar.HOUR_OF_DAY, atHour);
            calendar.set(Calendar.MINUTE, atMin);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND, 0);
            long result = (calendar.getTimeInMillis() - curTime) / 1000;
            return result;
        }
    }
}
