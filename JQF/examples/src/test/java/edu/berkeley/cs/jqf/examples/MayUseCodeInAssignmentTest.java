package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class MayUseCodeInAssignmentTest{ 

@Fuzz
public void fuzz(boolean writtenByYourself,boolean availableToOthers,boolean writtenAsCourseWork,boolean citingYourSource,boolean implementationRequired){
      MayUseCodeInAssignment.mayUseCodeInAssignment(writtenByYourself,availableToOthers,writtenAsCourseWork,citingYourSource,implementationRequired);
}
}