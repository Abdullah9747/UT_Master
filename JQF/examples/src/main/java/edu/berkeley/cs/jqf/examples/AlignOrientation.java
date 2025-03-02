package edu.berkeley.cs.jqf.examples;

public class AlignOrientation {
public static double alignOrientation(double baseOrientation, double orientation) {
        double resultOrientation;
        if (baseOrientation >= 0) {
            if (orientation < -Math.PI + baseOrientation)
                resultOrientation = orientation + 2 * Math.PI;
            else
                resultOrientation = orientation;

        } else if (orientation > +Math.PI + baseOrientation)
            resultOrientation = orientation - 2 * Math.PI;
        else
            resultOrientation = orientation;
        return resultOrientation;
    }
public static void main(String[] args) {


alignOrientation(87.29856125850598, 37.73163500171803);
}
}