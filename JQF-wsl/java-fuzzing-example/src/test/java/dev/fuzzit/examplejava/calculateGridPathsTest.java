package dev.fuzzit.examplejava;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class calculateGridPathsTest{ 

@Fuzz
public void fuzz(int rows,int cols,int[][] obstacles){
      calculateGridPaths.calculateGridPaths(rows,cols,obstacles);
}
}