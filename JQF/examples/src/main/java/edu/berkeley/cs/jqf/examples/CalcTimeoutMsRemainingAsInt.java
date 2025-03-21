package edu.berkeley.cs.jqf.examples;

public class CalcTimeoutMsRemainingAsInt {
static int calcTimeoutMsRemainingAsInt(long now, long deadlineMs) {
        long deltaMs = deadlineMs - now;
        if (deltaMs > Integer.MAX_VALUE)
            deltaMs = Integer.MAX_VALUE;
        else if (deltaMs < Integer.MIN_VALUE)
            deltaMs = Integer.MIN_VALUE;
        return (int) deltaMs;
    }
public static void main(String[] args) {


calcTimeoutMsRemainingAsInt(914656L, 647636L);
}
}