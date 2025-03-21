package edu.berkeley.cs.jqf.examples;

public class Humanize {
public static String humanize(long value) {
        if (value < 0) {
            return '-' + humanize(-value);
        } else if (value > 1000000000000000000L) {
            return Double.toString(
                    (value + 500000000000000L) / 1000000000000000L * 1000000000000000L / 1000000000000000000.0) + 'E';
        } else if (value > 100000000000000000L) {
            return Double.toString(
                    (value + 50000000000000L) / 100000000000000L * 100000000000000L / 1000000000000000.0) + 'P';
        } else if (value > 10000000000000000L) {
            return Double
                    .toString((value + 5000000000000L) / 10000000000000L * 10000000000000L / 1000000000000000.0) + 'P';
        } else if (value > 1000000000000000L) {
            return Double
                    .toString((value + 500000000000L) / 1000000000000L * 1000000000000L / 1000000000000000.0) + 'P';
        } else if (value > 100000000000000L) {
            return Double.toString((value + 50000000000L) / 100000000000L * 100000000000L / 1000000000000.0) + 'T';
        } else if (value > 10000000000000L) {
            return Double.toString((value + 5000000000L) / 10000000000L * 10000000000L / 1000000000000.0) + 'T';
        } else if (value > 1000000000000L) {
            return Double.toString((value + 500000000) / 1000000000 * 1000000000 / 1000000000000.0) + 'T';
        } else if (value > 100000000000L) {
            return Double.toString((value + 50000000) / 100000000 * 100000000 / 1000000000.0) + 'G';
        } else if (value > 10000000000L) {
            return Double.toString((value + 5000000) / 10000000 * 10000000 / 1000000000.0) + 'G';
        } else if (value > 1000000000) {
            return Double.toString((value + 500000) / 1000000 * 1000000 / 1000000000.0) + 'G';
        } else if (value > 100000000) {
            return Double.toString((value + 50000) / 100000 * 100000 / 1000000.0) + 'M';
        } else if (value > 10000000) {
            return Double.toString((value + 5000) / 10000 * 10000 / 1000000.0) + 'M';
        } else if (value > 1000000) {
            return Double.toString((value + 500) / 1000 * 1000 / 1000000.0) + 'M';
        } else if (value > 100000) {
            return Double.toString((value + 50) / 100 * 100 / 1000.0) + 'K';
        } else if (value > 10000) {
            return Double.toString((value + 5) / 10 * 10 / 1000.0) + 'K';
        } else if (value > 1000) {
            return Double.toString(value / 1000.0) + 'K';
        } else {
            return Long.toString(value) + ' ';
        }
    }
public static void main(String[] args) {


humanize(354906L);
}
}