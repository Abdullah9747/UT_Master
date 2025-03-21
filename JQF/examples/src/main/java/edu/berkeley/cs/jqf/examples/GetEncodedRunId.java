package edu.berkeley.cs.jqf.examples;

public class GetEncodedRunId {
public static long getEncodedRunId(long now) {
    // get top of the hour
    long lastHour = now - (now % 3600);
    // return inverted timestamp
    return (Long.MAX_VALUE - lastHour);
  }
public static void main(String[] args) {


getEncodedRunId(408322L);
}
}