package edu.berkeley.cs.jqf.examples;

public class Ordinalize {
public static String ordinalize(int n) {
    int mod100 = n % 100;
    if (mod100 == 11 || mod100 == 12 || mod100 == 13) {
      return String.valueOf(n) + "th";
    }
    switch (n % 10) {
      case 1:
        return String.valueOf(n) + "st";
      case 2:
        return String.valueOf(n) + "nd";
      case 3:
        return String.valueOf(n) + "rd";
      default:
        return String.valueOf(n) + "th";
    }
  }
public static void main(String[] args) {


ordinalize(15);
}
}