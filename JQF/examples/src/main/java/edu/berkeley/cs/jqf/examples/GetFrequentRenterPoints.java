package edu.berkeley.cs.jqf.examples;

public class GetFrequentRenterPoints {
public static int getFrequentRenterPoints(int daysRented) {
        return daysRented > 0 ? 1 : 0;
    }
public static void main(String[] args) {


getFrequentRenterPoints(28);
}
}