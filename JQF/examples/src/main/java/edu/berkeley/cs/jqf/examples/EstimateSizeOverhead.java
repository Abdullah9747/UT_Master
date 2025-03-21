package edu.berkeley.cs.jqf.examples;

public class EstimateSizeOverhead {
public static long estimateSizeOverhead(long size) {
      // We take 8 and add the number provided and then round up to 16 (& operator has higher precedence than +)
      return (size + 8 + 15) & ~15;
   }
public static void main(String[] args) {


estimateSizeOverhead(939012L);
}
}