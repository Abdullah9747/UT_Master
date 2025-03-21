package edu.berkeley.cs.jqf.examples;

public class BoolToInt {
public static int boolToInt(boolean value)
    {
        if (value)
        {
            return 1;
        }
        return 0;
    }
public static void main(String[] args) {


boolToInt(false);
}
}