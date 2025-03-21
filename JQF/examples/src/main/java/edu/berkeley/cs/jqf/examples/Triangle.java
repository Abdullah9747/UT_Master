package edu.berkeley.cs.jqf.examples;

public class Triangle {
public static int triangle(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            return 0;
        }

        // If sum of any two edges is greater than the length of the third edge
        if (a == b && b == c) {
            return 1;
        } else if (a + b > c && b + c > a && a + c > b) {
            return 2;
        }
        return 0;
    }
public static void main(String[] args) {


triangle(30, 52, 19);
}
}