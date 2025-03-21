package edu.berkeley.cs.jqf.examples;

public class HashLongToInt {
public static int hashLongToInt(long x) {

        /*
            int are 32 bits.
            long are 64 bits.

            So, an approach is to only look at the last 32 bits of the long.
            This can be achieved by doing a bitwise AND operation (&)
            on a 32 bit values with only 1s (ie 0xFF_FF_FF_FF).
            Note the "0x", this means representing a value in hexadecimal notations,
            where each symbol has 16 values: {0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F}.
            Being 16 values, can be represented with 16=2pow4 -> 4 bits, where F (15
            in decimal notation) is 1111 in binary, ie 8+4+2+1=15
            So, 32 bits of 1s is F 8 times, ie, 0xFFFFFFFF.
            When doing computations at bit/byte level, using hexadecimal is
            easier than decimal.

            A further thing to consider: in a int, if you have 32 bits of 1s, because
            the leftmost bit is a 1, then the value is negative, in particular:
            0xFFFFFFFF == -1
            however, in a long, that would be a:
            0xFFFFFFFFl == 4294967295
            ie (2pow32 - 1)

            so we could return

            (x & 0xFF_FF_FF_FFl)

            but that would be a long value, and we need an int.
            So we can cast it to an int with (int)

            (int) (x & 0xFF_FF_FF_FFl)

             but what does it mean to cast a long to int?
             In java, that just takes the rightmost 32-bits...
             so actually we do not need to do & bitmask
         */

        return (int) x;
    }
public static void main(String[] args) {


hashLongToInt(692527L);
}
}