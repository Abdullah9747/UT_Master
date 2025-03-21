package edu.berkeley.cs.jqf.examples;

public class ConvertMillisToMinSecFormat {
public static String convertMillisToMinSecFormat(long millis) {
        long ms = millis % 1000;
        long s = (millis / 1000) % 60;
        long m = ((millis / 1000) / 60) % 60;
        if (s > 0 && m <= 0) {
            return String.format("%d sec %d ms", s, ms);
        } else if (s > 0 || m > 0) {
            return String.format("%d min %d sec %d ms", m, s, ms);
        } else {
            return String.format("%d ms", ms);
        }
    }
public static void main(String[] args) {


convertMillisToMinSecFormat(876122L);
}
}