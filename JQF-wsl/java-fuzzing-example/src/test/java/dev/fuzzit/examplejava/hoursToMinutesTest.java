package dev.fuzzit.examplejava;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class hoursToMinutesTest{ 

@Fuzz
public void fuzz(int hours){
      hoursToMinutes.hoursToMinutes(hours);
}
}