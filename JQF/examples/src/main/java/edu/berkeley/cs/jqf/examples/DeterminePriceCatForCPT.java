package edu.berkeley.cs.jqf.examples;

public class DeterminePriceCatForCPT {
public static Integer determinePriceCatForCPT(double price)
    {
        if (price >= 0 && price <= 60000)
        {
            return 1;
        }
        if (price > 60000 && price <= 400000)
        {
            return 2;
        }
        if (price > 400000)
        {
            return 3;
        }
        return 3;
    }
public static void main(String[] args) {


determinePriceCatForCPT(92.05455838193544);
}
}