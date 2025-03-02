package edu.berkeley.cs.jqf.examples;

public class InRange {
public static boolean inRange(double d1, double d2, double range) {
    return Math.abs(d1 - d2) <= range;
  }
public static void main(String[] args) {


inRange(86.0086432817948, 54.1491267650758, 90.84295776446473);
}
}