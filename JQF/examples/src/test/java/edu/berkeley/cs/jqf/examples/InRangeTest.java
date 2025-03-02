package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class InRangeTest{ 

@Fuzz
public void fuzz(double d1,double d2,double range){
      InRange.inRange(d1,d2,range);
}
}