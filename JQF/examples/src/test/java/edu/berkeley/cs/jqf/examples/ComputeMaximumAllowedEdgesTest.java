package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class ComputeMaximumAllowedEdgesTest{ 

@Fuzz
public void fuzz(int n,boolean isDirected,boolean createLoops,boolean createMultipleEdges){
      ComputeMaximumAllowedEdges.computeMaximumAllowedEdges(n,isDirected,createLoops,createMultipleEdges);
}
}