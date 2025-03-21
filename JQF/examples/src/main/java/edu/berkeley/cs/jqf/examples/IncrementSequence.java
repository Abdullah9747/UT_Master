package edu.berkeley.cs.jqf.examples;

public class IncrementSequence {
static int incrementSequence(int baseSequence, int increment) {
        if (baseSequence > Integer.MAX_VALUE - increment)
            return increment - (Integer.MAX_VALUE - baseSequence) - 1;
        return baseSequence + increment;
    }
public static void main(String[] args) {


incrementSequence(33, 44);
}
}