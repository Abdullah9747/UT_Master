package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class ComputeAreaTest{ 

@Fuzz
public void fuzz(int A,int B,int C,int D,int E,int F,int G,int H){
      ComputeArea.computeArea(A,B,C,D,E,F,G,H);
}
}