package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class EncodeTrigramTest{ 

@Fuzz
public void fuzz(int g0,int g1,int g2){
      EncodeTrigram.encodeTrigram(g0,g1,g2);
}
}