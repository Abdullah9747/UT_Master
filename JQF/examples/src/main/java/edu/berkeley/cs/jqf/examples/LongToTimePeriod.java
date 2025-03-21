package edu.berkeley.cs.jqf.examples;

public class LongToTimePeriod {
public static String longToTimePeriod(long msec) {
        if (msec < 1000) {
            return msec + "ms";
        }
        if (msec < 3000) {
            return String.format("%.2fs", msec / 1000d);
        }
        if (msec < 60 * 1000) {
            return (msec / 1000) + "s";
        }
        long sec = msec / 1000;
        if (sec < 5 * 60) {
            return (sec / 60) + "m" + (sec % 60) + "s";
        }
        long min = sec / 60;
        if (min < 60) {
            return min + "m";
        }
        long hour = min / 60;
        if (min < 24 * 60) {
            return hour + "h" + (min % 60) + "m";
        }
        long day = hour / 24;
        return day + "d" + (day % 24) + "h";
    }
public static void main(String[] args) {


longToTimePeriod(114427L);
}
}