package edu.berkeley.cs.jqf.examples;

public class DeterminePriceCatForCPC {
public static Integer determinePriceCatForCPC(double price)
    {
        if (price >= 0.0 && price <= 0.7)
        {
            return 1;
        }
        if (price > 0.7 && price <= 2)
        {
            return 2;
        }
        if (price > 2)
        {
            return 3;
        }
        return 3;
    }
public static void main(String[] args) {


determinePriceCatForCPC(32.89188625663712);
}
}