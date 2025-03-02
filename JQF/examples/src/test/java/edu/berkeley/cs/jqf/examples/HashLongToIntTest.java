package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class HashLongToIntTest{ 

@Fuzz
public void fuzz(long x){
      HashLongToInt.hashLongToInt(x);
}
}