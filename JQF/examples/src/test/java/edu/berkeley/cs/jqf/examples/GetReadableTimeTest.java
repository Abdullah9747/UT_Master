package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class GetReadableTimeTest{ 

@Fuzz
public void fuzz(long time_ms){
      GetReadableTime.getReadableTime(time_ms);
}
}