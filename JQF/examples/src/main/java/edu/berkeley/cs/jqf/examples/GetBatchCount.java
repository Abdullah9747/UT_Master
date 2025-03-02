package edu.berkeley.cs.jqf.examples;

public class GetBatchCount {
public static int getBatchCount(int length, int batchSize) {
    // Avoid negative numbers.
    if ((batchSize < 1) || (length < 1)) {
      return 0;
    }
    int remainder = length % batchSize;
    return (remainder > 0) ? (length / batchSize) + 1 : (length / batchSize);
  }
public static void main(String[] args) {


getBatchCount(91, 42);
}
}