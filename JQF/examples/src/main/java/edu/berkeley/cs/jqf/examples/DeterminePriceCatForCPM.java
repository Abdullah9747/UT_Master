package edu.berkeley.cs.jqf.examples;

public class DeterminePriceCatForCPM {
public static Integer determinePriceCatForCPM(double price)
    {
        if (price >= 0.0 && price <= 12)
        {
            return 1;
        }
        if (price > 12 && price <= 25)
        {
            return 2;
        }
        if (price > 25)
        {
            return 3;
        }
        return 3;
    }
public static void main(String[] args) {


determinePriceCatForCPM(21.07464209375489);
}
}