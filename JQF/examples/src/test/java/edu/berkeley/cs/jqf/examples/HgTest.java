package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class HgTest{ 

@Fuzz
public void fuzz(long D,long gt,long S){
      Hg.hg(D,gt,S);
}
}