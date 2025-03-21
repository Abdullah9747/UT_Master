package edu.berkeley.cs.jqf.examples;

public class EncodeTrigram {
public static long encodeTrigram(int g0, int g1, int g2) {
    long encoded = g2;
    encoded = (encoded << 21) | g1;
    return (encoded << 21) | g0;
  }
public static void main(String[] args) {


encodeTrigram(72, 38, 34);
}
}