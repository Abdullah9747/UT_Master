package edu.berkeley.cs.jqf.examples;

public class MayUseCodeInAssignment {
public static boolean mayUseCodeInAssignment(boolean writtenByYourself,
            boolean availableToOthers, boolean writtenAsCourseWork,
            boolean citingYourSource, boolean implementationRequired) {
        
        // TODO: Fill in this method, then remove the exception
    	return writtenByYourself && !availableToOthers
    			&& writtenAsCourseWork && citingYourSource
    			&& implementationRequired;
    }
public static void main(String[] args) {


mayUseCodeInAssignment(false, false, false, false, true);
}
}