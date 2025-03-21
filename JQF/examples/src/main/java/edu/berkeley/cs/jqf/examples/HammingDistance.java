package edu.berkeley.cs.jqf.examples;

public class HammingDistance {
public static int hammingDistance(int x, int y) {
    String str1 = Integer.toBinaryString(x);
    String str2 = Integer.toBinaryString(y);
    int length1 = str1.length();
    int length2 = str2.length();
    int shortLength = Math.min(length1, length2);
    int result = 0;
    for (int i = 1; i <= shortLength; i++) {
      if (str1.charAt(length1 - i) != str2.charAt(length2 - i)) {
        result++;
      }
    }
    while (shortLength < length1) {
      shortLength ++;
      if(str1.charAt(length1 - shortLength) != '0'){
        result ++;
      }
    }
    while (shortLength < length2) {
      shortLength ++;
      if(str2.charAt(length2 - shortLength) != '0'){
        result ++;
      }
    }
    return result;
  }
public static void main(String[] args) {


hammingDistance(78, 66);
}
}