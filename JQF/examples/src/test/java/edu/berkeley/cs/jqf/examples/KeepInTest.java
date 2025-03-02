package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class KeepInTest{ 

@Fuzz
public void fuzz(double value,double min,double max){
      KeepIn.keepIn(value,min,max);
}
}