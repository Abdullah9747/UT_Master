package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class ComputeTargetQuiTest{ 

@Fuzz
public void fuzz(boolean implicit,double value,double currentValue){
      ComputeTargetQui.computeTargetQui(implicit,value,currentValue);
}
}