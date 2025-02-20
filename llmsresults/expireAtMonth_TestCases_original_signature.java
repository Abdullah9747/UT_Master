import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Calendar.class)
public class ExpireAtMonthTest {

    private long expireAtMonth(int addMonth, int atDay, int atHour, int atMin) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.add(Calendar.MONTH, addMonth);
        cal.set(Calendar.DAY_OF_MONTH, atDay);
        cal.set(Calendar.HOUR_OF_DAY, atHour);
        cal.set(Calendar.MINUTE, atMin);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    @Test
    public void testBasicPositiveCase() {
        Calendar mockCalendar = Mockito.mock(Calendar.class);
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance(TimeZone.getTimeZone("UTC"))).thenReturn(mockCalendar);

        LocalDateTime now = LocalDateTime.of(2024, 1, 1, 0, 0); // Set a fixed current time
        when(mockCalendar.getTimeInMillis()).thenAnswer(invocation -> now.toInstant(ZoneOffset.UTC).toEpochMilli());
        when(mockCalendar.getTimeZone()).thenReturn(TimeZone.getTimeZone("UTC"));

        long expectedTimestamp = LocalDateTime.of(2024, 2, 15, 12, 30).toInstant(ZoneOffset.UTC).toEpochMilli();
        long actualTimestamp = expireAtMonth(1, 15, 12, 30);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(actualTimestamp);
        assertEquals(LocalDateTime.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)), LocalDateTime.of(2024, 2, 15, 12, 30));

    }

    @Test
    public void testMonthRolloverToNextYear() {
        Calendar mockCalendar = Mockito.mock(Calendar.class);
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance(TimeZone.getTimeZone("UTC"))).thenReturn(mockCalendar);

        LocalDateTime now = LocalDateTime.of(2023, 11, 1, 0, 0); // November
        when(mockCalendar.getTimeInMillis()).thenAnswer(invocation -> now.toInstant(ZoneOffset.UTC).toEpochMilli());
        when(mockCalendar.getTimeZone()).thenReturn(TimeZone.getTimeZone("UTC"));

        long expectedTimestamp = LocalDateTime.of(2024, 1, 10, 8, 0).toInstant(ZoneOffset.UTC).toEpochMilli();
        long actualTimestamp = expireAtMonth(2, 10, 8, 0);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(actualTimestamp);
        assertEquals(LocalDateTime.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)), LocalDateTime.of(2024, 1, 10, 8, 0));
    }

    @Test
    public void testAtDayIsLastDayOfMonth() {
        Calendar mockCalendar = Mockito.mock(Calendar.class);
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance(TimeZone.getTimeZone("UTC"))).thenReturn(mockCalendar);

        LocalDateTime now = LocalDateTime.of(2024, 1, 1, 0, 0); // January
        when(mockCalendar.getTimeInMillis()).thenAnswer(invocation -> now.toInstant(ZoneOffset.UTC).toEpochMilli());
        when(mockCalendar.getTimeZone()).thenReturn(TimeZone.getTimeZone("UTC"));

        long expectedTimestamp = LocalDateTime.of(2024, 2, 29, 18, 45).toInstant(ZoneOffset.UTC).toEpochMilli();
        long actualTimestamp = expireAtMonth(1, 29, 18, 45);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(actualTimestamp);
        assertEquals(LocalDateTime.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)), LocalDateTime.of(2024, 2, 29, 18, 45));
    }

    @Test
    public void testAtDayIsGreaterThanDaysInTargetMonth() {
        Calendar mockCalendar = Mockito.mock(Calendar.class);
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance(TimeZone.getTimeZone("UTC"))).thenReturn(mockCalendar);

        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0, 0); // March
        when(mockCalendar.getTimeInMillis()).thenAnswer(invocation -> now.toInstant(ZoneOffset.UTC).toEpochMilli());
        when(mockCalendar.getTimeZone()).thenReturn(TimeZone.getTimeZone("UTC"));

        long expectedTimestamp = LocalDateTime.of(2024, 4, 30, 9, 15).toInstant(ZoneOffset.UTC).toEpochMilli(); // April only has 30 days.  Adjusting to last day.
        long actualTimestamp = expireAtMonth(1, 31, 9, 15);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(actualTimestamp);
        assertEquals(LocalDateTime.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)), LocalDateTime.of(2024, 4, 30, 9, 15));
    }

    @Test
    public void testZeroValues() {
        Calendar mockCalendar = Mockito.mock(Calendar.class);
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance(TimeZone.getTimeZone("UTC"))).thenReturn(mockCalendar);

        LocalDateTime now = LocalDateTime.of(2024, 5, 10, 10, 0);
        when(mockCalendar.getTimeInMillis()).thenAnswer(invocation -> now.toInstant(ZoneOffset.UTC).toEpochMilli());
        when(mockCalendar.getTimeZone()).thenReturn(TimeZone.getTimeZone("UTC"));

        long expectedTimestamp = LocalDateTime.of(2024, 5, 1, 0, 0).toInstant(ZoneOffset.UTC).toEpochMilli();
        long actualTimestamp = expireAtMonth(0, 1, 0, 0);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(actualTimestamp);
        assertEquals(LocalDateTime.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)), LocalDateTime.of(2024, 5, 1, 0, 0));
    }

    @Test
    public void testLargeAddMonthValue() {
        Calendar mockCalendar = Mockito.mock(Calendar.class);
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance(TimeZone.getTimeZone("UTC"))).thenReturn(mockCalendar);

        LocalDateTime now = LocalDateTime.of(2024, 1, 1, 0, 0);
        when(mockCalendar.getTimeInMillis()).thenAnswer(invocation -> now.toInstant(ZoneOffset.UTC).toEpochMilli());
        when(mockCalendar.getTimeZone()).thenReturn(TimeZone.getTimeZone("UTC"));

        long expectedTimestamp = LocalDateTime.of(2025, 4, 22, 16, 5).toInstant(ZoneOffset.UTC).toEpochMilli();
        long actualTimestamp = expireAtMonth(15, 22, 16, 5);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(actualTimestamp);
        assertEquals(LocalDateTime.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)), LocalDateTime.of(2025, 4, 22, 16, 5));
    }

    @Test
    public void testEdgeCasesForTime() {
        Calendar mockCalendar = Mockito.mock(Calendar.class);
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance(TimeZone.getTimeZone("UTC"))).thenReturn(mockCalendar);

        LocalDateTime now = LocalDateTime.of(2024, 1, 1, 0, 0);
        when(mockCalendar.getTimeInMillis()).thenAnswer(invocation -> now.toInstant(ZoneOffset.UTC).toEpochMilli());
        when(mockCalendar.getTimeZone()).thenReturn(TimeZone.getTimeZone("UTC"));

        long expectedTimestamp = LocalDateTime.of(2024, 2, 15, 23, 59).toInstant(ZoneOffset.UTC).toEpochMilli();
        long actualTimestamp = expireAtMonth(1, 15, 23, 59);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(actualTimestamp);
        assertEquals(LocalDateTime.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)), LocalDateTime.of(2024, 2, 15, 23, 59));
    }

    @Test
    public void testCurrentDateNearEndOfMonth() {
        Calendar mockCalendar = Mockito.mock(Calendar.class);
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance(TimeZone.getTimeZone("UTC"))).thenReturn(mockCalendar);

        LocalDateTime now = LocalDateTime.of(2024, 1, 31, 0, 0);
        when(mockCalendar.getTimeInMillis()).thenAnswer(invocation -> now.toInstant(ZoneOffset.UTC).toEpochMilli());
        when(mockCalendar.getTimeZone()).thenReturn(TimeZone.getTimeZone("UTC"));

        long expectedTimestamp = LocalDateTime.of(2024, 2, 15, 12, 30).toInstant(ZoneOffset.UTC).toEpochMilli();
        long actualTimestamp = expireAtMonth(1, 15, 12, 30);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(actualTimestamp);
        assertEquals(LocalDateTime.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)), LocalDateTime.of(2024, 2, 15, 12, 30));
    }
}
