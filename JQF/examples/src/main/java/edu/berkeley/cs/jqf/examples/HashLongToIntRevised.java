package edu.berkeley.cs.jqf.examples;

public class HashLongToIntRevised {
public static int hashLongToIntRevised(long x) {
        /*
            This is actually what done in the Java API to
            calculate Long.hashCode().

            All bits of the inputs are used to calculate the hash.

            x >>> 32 does shift all the bits in x by 32 positions to the right.
            Once that is executed, the results will have the 32 leftmost bits
            into the 32 rightmost (which are now discarded).

            With the exclusive OR operation (^), we compare each bit between
            "x" and "x >>> 32", and create a new value based on whether they
            disagree on the 1s (only one of the two values has a 1).

            Recall that the (int) will ignore the 32 leftmost bits.
            So, the resulting integer from the 32 bits is based on a XOR
            between the first and last 32 bits of the original input.

            Why using a XOR? reason is, given any input value x, if we just
            change one single bit, then necessarily we will end up with
            a different hash value.
            Changing 2 or more bits might result in same hash though.
         */
        return (int) (x ^ (x >>> 32));
    }
public static void main(String[] args) {


hashLongToIntRevised(715802L);
}
}