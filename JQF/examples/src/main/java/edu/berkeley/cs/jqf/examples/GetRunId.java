package edu.berkeley.cs.jqf.examples;

public class GetRunId {
public static long getRunId(long encodedRunId) {
    return Long.MAX_VALUE - encodedRunId;
  }
public static void main(String[] args) {


getRunId(416998L);
}
}