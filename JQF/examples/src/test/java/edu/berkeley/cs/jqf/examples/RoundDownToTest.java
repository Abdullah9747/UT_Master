package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class RoundDownToTest{ 

@Fuzz
public void fuzz(int value,int modulus){
      RoundDownTo.roundDownTo(value,modulus);
}
}