package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class MultiplyTest{ 

@Fuzz
public void fuzz(long factor1,long factor2){
      Multiply.multiply(factor1,factor2);
}
}