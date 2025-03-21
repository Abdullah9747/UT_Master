package edu.berkeley.cs.jqf.examples;

public class Threes {
public static int threes(int d1, int d2, int d3, int d4, int d5) {
        int s;
        s = 0;
        if (d1 == 3) s += 3;
        if (d2 == 3) s += 3;
        if (d3 == 3) s += 3;
        if (d4 == 3) s += 3;
        if (d5 == 3) s += 3;
        return s;
    }
public static void main(String[] args) {


threes(52, 21, 28, 50, 73);
}
}