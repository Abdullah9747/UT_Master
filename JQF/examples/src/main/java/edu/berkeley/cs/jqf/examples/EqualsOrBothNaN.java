package edu.berkeley.cs.jqf.examples;

public class EqualsOrBothNaN {
public static boolean equalsOrBothNaN(double val1, double val2)
    {
        return val1 == val2 || Double.isNaN(val1) && Double.isNaN(val2);
    }
public static void main(String[] args) {


equalsOrBothNaN(55.32394205933874, 46.233644760997464);
}
}