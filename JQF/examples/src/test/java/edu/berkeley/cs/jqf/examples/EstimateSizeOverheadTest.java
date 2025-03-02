package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class EstimateSizeOverheadTest{ 

@Fuzz
public void fuzz(long size){
      EstimateSizeOverhead.estimateSizeOverhead(size);
}
}