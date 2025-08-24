package demo; import gov.nasa.jpf.symbc.Debug;public class Wrap {
public static int wrap(int value, int min, int max)
    {
        int newValue = value;
        final int step = max - min;

        if (newValue >= max)
        {
            while (newValue >= max)
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
int x0 = Debug.makeSymbolicInteger("x0");
int x1 = Debug.makeSymbolicInteger("x1");
int x2 = Debug.makeSymbolicInteger("x2");
wrap(x0,x1,x2);
}}