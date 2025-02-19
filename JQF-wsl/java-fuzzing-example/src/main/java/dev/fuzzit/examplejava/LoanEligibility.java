package dev.fuzzit.examplejava;

public class LoanEligibility {
    public static boolean checkEligibility(int age, int creditScore, double income, double loanAmount, int loanTenure) {
        if (age < 18 || age > 65) {
            return false; // Age not eligible
        } 
        if (creditScore < 300 || creditScore > 850) {
            return false; // Invalid credit score
        } 
        double maxLoan = income * (loanTenure / 10.0);
        if (loanAmount > maxLoan) {
            return false; // Loan exceeds allowable limit
        }
        if (creditScore >= 750 && income > 50000) {
            return true; // Automatically eligible for high credit score and income
        } 
        if (creditScore >= 600 && loanTenure <= 10) {
            return true; // Moderate eligibility
        }
        return false; // Default rejection
    }
public static void main(String[] args) {


checkEligibility(50, 66, 92.15830495473955, 56.55098364116769, 87);
}
}