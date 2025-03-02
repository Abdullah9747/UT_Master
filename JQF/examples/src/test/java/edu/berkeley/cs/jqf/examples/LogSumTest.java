package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class LogSumTest{ 

@Fuzz
public void fuzz(double logA,double logB){
      LogSum.logSum(logA,logB);
}
}