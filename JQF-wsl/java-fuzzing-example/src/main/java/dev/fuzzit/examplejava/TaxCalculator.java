package dev.fuzzit.examplejava;

public class TaxCalculator {
    public static double calculateTax(double income, int dependents, boolean hasInvestments) {
        if (income < 0 || dependents < 0) {
            throw new IllegalArgumentException("Invalid input values");
        }
        double taxRate = income > 100000 ? 0.3 : income > 50000 ? 0.2 : 0.1;
        double baseTax = income * taxRate;
        if (dependents > 0) {
            baseTax -= dependents * 2000; // Deduction per dependent
        }
        if (hasInvestments) {
            baseTax *= 0.85; // Investment rebate
        }
        return Math.max(baseTax, 0); // Ensure tax is not negative
    }

public static void main(String[] args) {


calculateTax(91.92026761080376, 98, false);
}
}