package demo; import gov.nasa.jpf.symbc.Debug;public class ProbabilityOfMean {
double probabilityOfMean(double mean) {
        if (mean <= 0)
            throw new IllegalArgumentException("Need a positive mean, got " + mean);

        return 1 / mean;
    }
public static void main(String[] args){
double x0 = Debug.makeSymbolicReal("x0");
probabilityOfMean(x0);
}}