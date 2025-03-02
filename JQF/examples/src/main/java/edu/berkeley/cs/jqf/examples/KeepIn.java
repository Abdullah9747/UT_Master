package edu.berkeley.cs.jqf.examples;

public class KeepIn {
public static final double keepIn(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }
public static void main(String[] args) {


keepIn(69.01656163214759, 47.918676124952526, 32.63490836524625);
}
}