package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class EqualsOrBothNaNTest{ 

@Fuzz
public void fuzz(double val1,double val2){
      EqualsOrBothNaN.equalsOrBothNaN(val1,val2);
}
}