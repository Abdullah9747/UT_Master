package edu.berkeley.cs.jqf.examples;

public class ToMillis {
static long toMillis(long timestamp) {
    if (timestamp < 10000000000L) {
      // in seconds
      return timestamp * 1000;
    } else if (timestamp < 10000000000000L) {
      // in millis
      return timestamp;
    }
    // in micros
    return timestamp / 1000;
  }
public static void main(String[] args) {


toMillis(165567L);
}
}