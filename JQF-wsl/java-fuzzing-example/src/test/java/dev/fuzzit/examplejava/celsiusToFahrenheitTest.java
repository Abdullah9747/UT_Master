package dev.fuzzit.examplejava;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class celsiusToFahrenheitTest{ 

@Fuzz
public void fuzz(int celsius){
      celsiusToFahrenheit.celsiusToFahrenheit(celsius);
}
}