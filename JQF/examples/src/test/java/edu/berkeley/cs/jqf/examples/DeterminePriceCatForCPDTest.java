package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class DeterminePriceCatForCPDTest{ 

@Fuzz
public void fuzz(double price){
      DeterminePriceCatForCPD.determinePriceCatForCPD(price);
}
}