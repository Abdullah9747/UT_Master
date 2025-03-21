package edu.berkeley.cs.jqf.examples;

public class GetOrdinalFor {
public static String getOrdinalFor(int value) {

    String ordinal = String.valueOf(value);
    if (ordinal.endsWith("11") || ordinal.endsWith("12") || ordinal.endsWith("13")) {
      ordinal = ordinal + "th";
    } else if (ordinal.endsWith("1")) {
      ordinal = ordinal + "st";
    } else if (ordinal.endsWith("2")) {
      ordinal = ordinal + "nd";
    } else if (ordinal.endsWith("3")) {
      ordinal = ordinal + "rd";
    } else {
      ordinal = ordinal + "th";
    }
    return ordinal;
  }
public static void main(String[] args) {


getOrdinalFor(93);
}
}