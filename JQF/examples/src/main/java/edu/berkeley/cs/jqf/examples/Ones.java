package edu.berkeley.cs.jqf.examples;

public class Ones {
public static int ones(int d1, int d2, int d3, int d4, int d5) {
        int sum = 0;
        if (d1 == 1) sum++;
        if (d2 == 1) sum++;
        if (d3 == 1) sum++;
        if (d4 == 1) sum++;
        if (d5 == 1)
            sum++;

        return sum;
    }
public static void main(String[] args) {


ones(58, 84, 29, 3, 67);
}
}