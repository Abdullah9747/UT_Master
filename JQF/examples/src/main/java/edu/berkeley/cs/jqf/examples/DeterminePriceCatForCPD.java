package edu.berkeley.cs.jqf.examples;

public class DeterminePriceCatForCPD {
public static Integer determinePriceCatForCPD(double price)
    {
        if (price >= 0 && price <= 3)
        {
            return 1;
        }
        if (price > 3 && price <= 5)
        {
            return 2;
        }
        if (price > 5)
        {
            return 3;
        }
        return 3;
    }
public static void main(String[] args) {


determinePriceCatForCPD(54.03581752141236);
}
}