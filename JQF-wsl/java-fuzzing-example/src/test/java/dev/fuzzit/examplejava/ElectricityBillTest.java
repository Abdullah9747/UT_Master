package dev.fuzzit.examplejava;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class ElectricityBillTest{ 

@Fuzz
public void fuzz(int unitsConsumed,double ratePerUnit,boolean isPeakMonth){
      ElectricityBill.calculateBill(unitsConsumed,ratePerUnit,isPeakMonth);
}
}