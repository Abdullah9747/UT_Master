package demo; import gov.nasa.jpf.symbc.Debug;public class TaxCalculator {
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

public static void main(String[] args){
double x0 = Debug.makeSymbolicReal("x0");
int x1 = Debug.makeSymbolicInteger("x1");
boolean x2 = Debug.makeSymbolicBoolean("x2");
taxCalculator(x0,x1,x2);
}}