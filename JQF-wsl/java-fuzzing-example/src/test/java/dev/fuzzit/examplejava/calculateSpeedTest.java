package dev.fuzzit.examplejava;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class calculateSpeedTest{ 

@Fuzz
public void fuzz(int distance,int time){
      calculateSpeed.calculateSpeed(distance,time);
}
}