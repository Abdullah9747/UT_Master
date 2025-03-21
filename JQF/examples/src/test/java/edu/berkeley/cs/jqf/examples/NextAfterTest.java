package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class NextAfterTest{ 

@Fuzz
public void fuzz(double d,double direction){
      NextAfter.nextAfter(d,direction);
}
}