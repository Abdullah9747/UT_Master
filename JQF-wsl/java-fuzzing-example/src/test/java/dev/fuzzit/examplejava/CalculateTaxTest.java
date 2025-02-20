package dev.fuzzit.examplejava;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class calculateTaxTest{ 

@Fuzz
public void fuzz(double income,int dependents,boolean hasInvestments){
      calculateTax.calculateTax(income,dependents,hasInvestments);
}
}