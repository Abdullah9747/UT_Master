package demo; import gov.nasa.jpf.symbc.Debug;public class WrapDouble {
public static double wrapDouble(double value, double min, double max)
    {
        double newValue = value;
        final double step = max - min;

        if (Double.compare(newValue, max) >= 0)
        {
            while (Double.compare(newValue, max) >= 0)
            {
                newValue -= step;
            }
        }
        else if (newValue < min)
        {
            while (newValue < min)
            {
                newValue += step;
            }
        }
        return newValue;
    }
public static void main(String[] args){
double x0 = Debug.makeSymbolicReal("x0");
double x1 = Debug.makeSymbolicReal("x1");
double x2 = Debug.makeSymbolicReal("x2");
wrapDouble(x0,x1,x2);
}}