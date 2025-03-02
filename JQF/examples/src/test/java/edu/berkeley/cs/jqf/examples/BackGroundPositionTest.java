package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class BackGroundPositionTest{ 

@Fuzz
public void fuzz(int clipX,int clipY){
      BackGroundPosition.backGroundPosition(clipX,clipY);
}
}