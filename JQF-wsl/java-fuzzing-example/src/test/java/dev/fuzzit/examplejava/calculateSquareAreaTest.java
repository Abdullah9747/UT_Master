package dev.fuzzit.examplejava;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class calculateSquareAreaTest{ 

@Fuzz
public void fuzz(int side){
      calculateSquareArea.calculateSquareArea(side);
}
}