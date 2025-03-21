package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class TypeTest{ 

@Fuzz
public void fuzz(int Side1,int Side2,int Side3){
      Type.type(Side1,Side2,Side3);
}
}