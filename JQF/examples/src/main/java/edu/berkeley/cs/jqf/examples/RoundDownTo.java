package edu.berkeley.cs.jqf.examples;

public class RoundDownTo {
public static int roundDownTo(int value, int modulus)
    {
        return value >= 0 ? value - value % modulus : value + (value % modulus == 0 ? 0 : -modulus - value % modulus);
    }
public static void main(String[] args) {


roundDownTo(4, 13);
}
}