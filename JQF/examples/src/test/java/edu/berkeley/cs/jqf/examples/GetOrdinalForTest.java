package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class GetOrdinalForTest{ 

@Fuzz
public void fuzz(int value){
      GetOrdinalFor.getOrdinalFor(value);
}
}