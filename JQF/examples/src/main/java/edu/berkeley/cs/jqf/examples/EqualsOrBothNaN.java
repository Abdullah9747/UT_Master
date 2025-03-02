package edu.berkeley.cs.jqf.examples;

public class EqualsOrBothNaN {
public static boolean equalsOrBothNaN(double val1, double val2)
    {
        return val1 == val2 || Double.isNaN(val1) && Double.isNaN(val2);
    }
public static void main(String[] args) {


equalsOrBothNaN(23.037622069003277, 42.56689673474058);
}
}