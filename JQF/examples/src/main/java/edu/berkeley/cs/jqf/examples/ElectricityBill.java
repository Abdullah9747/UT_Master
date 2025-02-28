package edu.berkeley.cs.jqf.examples;

public class ElectricityBill {
    public static double calculateBill(int unitsConsumed, double ratePerUnit, boolean isPeakMonth) {
        if (unitsConsumed < 0 || ratePerUnit <= 0) {
            throw new IllegalArgumentException("Invalid input values");
        }
        double bill = unitsConsumed * ratePerUnit;
        if (isPeakMonth) {
            bill *= 1.2; // Surcharge during peak months
        }
        if (unitsConsumed > 500) {
            bill += 50; // Additional fixed charge for high usage
        } else if (unitsConsumed < 100) {
            bill *= 0.9; // Discount for low usage
        }
        return bill;
    }

    public static void main(String[] args) {

        calculateBill(9, 44.194329864732374, false);
    }
}