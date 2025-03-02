package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class ClampTest{ 

@Fuzz
public void fuzz(int value,int min,int max){
      Clamp.clamp(value,min,max);
}
}