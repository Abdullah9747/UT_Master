package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class MedianTest{ 

@Fuzz
public void fuzz(int x,int y,int z){
      Median.median(x,y,z);
}
}