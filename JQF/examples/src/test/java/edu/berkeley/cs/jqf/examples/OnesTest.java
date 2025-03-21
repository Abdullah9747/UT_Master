package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class OnesTest{ 

@Fuzz
public void fuzz(int d1,int d2,int d3,int d4,int d5){
      Ones.ones(d1,d2,d3,d4,d5);
}
}