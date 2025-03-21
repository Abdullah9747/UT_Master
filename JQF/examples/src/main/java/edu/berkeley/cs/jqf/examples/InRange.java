package edu.berkeley.cs.jqf.examples;

public class InRange {
public static boolean inRange(double d1, double d2, double range) {
    return Math.abs(d1 - d2) <= range;
  }
public static void main(String[] args) {


inRange(66.0821297121783, 66.8322839065841, 75.14653828203159);
}
}