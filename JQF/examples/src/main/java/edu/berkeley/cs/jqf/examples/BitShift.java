package edu.berkeley.cs.jqf.examples;

public class BitShift {
public static long bitShift(long inputVal, int shift)
    {
        return shift > 0 ? inputVal << shift : shift < 0 ? inputVal >> shift * -1 : inputVal;
    }
public static void main(String[] args) {


bitShift(737065L, 36);
}
}