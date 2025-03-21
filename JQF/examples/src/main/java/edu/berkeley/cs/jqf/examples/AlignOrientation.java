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


alignOrientation(91.34517800533548, 65.41249806709878);
}
}