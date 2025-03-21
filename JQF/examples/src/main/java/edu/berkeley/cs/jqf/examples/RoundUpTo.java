package edu.berkeley.cs.jqf.examples;

public class RoundUpTo {
public static int roundUpTo(int value, int modulus)
    {
        return value > 0 ? value % modulus == 0 ? value : value + modulus - value % modulus : value - value % modulus;
    }
public static void main(String[] args) {


roundUpTo(35, 43);
}
}