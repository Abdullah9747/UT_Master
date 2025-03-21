package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class DecreaseFactorTest{ 

@Fuzz
public void fuzz(int factor){
      DecreaseFactor.decreaseFactor(factor);
}
}