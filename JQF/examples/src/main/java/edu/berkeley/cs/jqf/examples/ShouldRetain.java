package edu.berkeley.cs.jqf.examples;

public class ShouldRetain {
public static boolean shouldRetain(int i, int maxRetention, int length) {
    // Files with a zero-based index greater or equal than the retentionCutoff
    // should be retained.
    int retentionCutoff = length - maxRetention;
    boolean retain = (i >= retentionCutoff) ? true : false;
    return retain;
  }
public static void main(String[] args) {


shouldRetain(5, 1, 83);
}
}