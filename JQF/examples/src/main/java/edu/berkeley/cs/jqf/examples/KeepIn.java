package edu.berkeley.cs.jqf.examples;

public class KeepIn {
public static final double keepIn(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }
public static void main(String[] args) {


keepIn(15.97406120952337, 77.03764114201087, 74.69001473565274);
}
}