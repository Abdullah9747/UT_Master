package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class CalcTimeoutMsRemainingAsIntTest{ 

@Fuzz
public void fuzz(long now,long deadlineMs){
      CalcTimeoutMsRemainingAsInt.calcTimeoutMsRemainingAsInt(now,deadlineMs);
}
}