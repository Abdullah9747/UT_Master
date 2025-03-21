package edu.berkeley.cs.jqf.examples;

public class DecreaseFactor {
static int decreaseFactor(int factor) {
        if (factor == 1) {
            return 1;
        }

        int order = 1;
        while (factor >= 10) {
            factor /= 10;
            order *= 10;
        }

        if (factor == 1) {
            return order / 2;
        }

        if (factor == 5) {
            return order * 2;
        }

        if (factor == 2) {
            return order;
        }

        throw new IllegalStateException("Logic error: this should be unreachable");
    }
public static void main(String[] args) {


decreaseFactor(31);
}
}