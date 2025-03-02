package edu.berkeley.cs.jqf.examples;

public class SumOverflow {
public static boolean sumOverflow(long a, long b)
    {
        return b > Long.MAX_VALUE - a;
    }
public static void main(String[] args) {


sumOverflow(427742L, 33561L);
}
}