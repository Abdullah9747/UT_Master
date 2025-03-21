package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class GetCurrentBarIndexTest{ 

@Fuzz
public void fuzz(int barCount,int currentBar,int index){
      GetCurrentBarIndex.getCurrentBarIndex(barCount,currentBar,index);
}
}