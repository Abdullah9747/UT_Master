package edu.berkeley.cs.jqf.examples;

public class Gcd {
public static int gcd(int i, int j)
    {
        int a = i;
        int b = j;
        while (b != 0)
        {
            a ^= b;
            b ^= a;
            a ^= b;
            b %= a;
        }
        return a >= 0 ? a : -a;
    }
public static void main(String[] args) {


gcd(88, 29);
}
}