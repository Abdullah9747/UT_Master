package edu.berkeley.cs.jqf.examples;

public class Clamp {
public static int clamp(int value, int min, int max)
    {
        final int fixed;
        if (value < min)
        {
            fixed = min;
        }
        else if (value > max)
        {
            fixed = max;
        }
        else
        {
            fixed = value;
        }
        return fixed;
    }
public static void main(String[] args) {


clamp(97, 74, 51);
}
}