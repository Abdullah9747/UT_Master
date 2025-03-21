package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class Build_lenenc_intTest{ 

@Fuzz
public void fuzz(long value){
      Build_lenenc_int.build_lenenc_int(value);
}
}