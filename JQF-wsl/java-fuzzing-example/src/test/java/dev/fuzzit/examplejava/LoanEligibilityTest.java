package dev.fuzzit.examplejava;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class LoanEligibilityTest{ 

@Fuzz
public void fuzz(int age,int creditScore,double income,double loanAmount,int loanTenure){
      LoanEligibility.checkEligibility(age,creditScore,income,loanAmount,loanTenure);
}
}