package edu.berkeley.cs.jqf.examples;

public class GetReadableTime {
public static String getReadableTime(long time_ms) {
        StringBuilder output = new StringBuilder();
        long time_delta = time_ms;

        int milliseconds = (int) (time_delta % 1000);
        time_delta /= 1000;
        int seconds = (int) (time_delta % 60);
        time_delta /= 60;
        int minutes = (int) (time_delta % 60);
        time_delta /= 60;
        int hours = (int) (time_delta % 24);
        int days = (int) (time_delta / 24);

        if (days != 0) {
            output.append(days);
            output.append(" day");
            if (days > 1) {
                output.append("s");
            }
        }
        if ((hours != 0) || (minutes != 0)) {
            if (output.length() > 0) {
                // Use zero-padded hours here as it's longer than a day.
                output.append(String.format(" %02d:%02d:%02d", hours, minutes, seconds));
            } else {
                // Don't pad hours if less than a day.
                output.append(String.format("%d:%02d:%02d", hours, minutes, seconds));
            }
        } else if (output.length() > 0) {
            /*
             * If a day+ with zero hours and zero minutes, just report the days.
             * E.g. "1 day", and not "1 day 35 ms".
             */
            return output.toString();
        } else if (seconds != 0) {
            output.append(String.format("%d.%d seconds", seconds, milliseconds));
        } else if (milliseconds != 0) {
            output.append(String.format("%d ms", milliseconds));
        }

        return (output.length() == 0 ? "0 ms" : output.toString());
    }
public static void main(String[] args) {


getReadableTime(38312L);
}
}