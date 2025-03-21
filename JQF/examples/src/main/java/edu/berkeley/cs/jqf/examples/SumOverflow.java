package edu.berkeley.cs.jqf.examples;

public class SumOverflow {
public static boolean sumOverflow(long a, long b)
    {
        return b > Long.MAX_VALUE - a;
    }
public static void main(String[] args) {


sumOverflow(35421L, 458328L);
}
}