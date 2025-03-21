package edu.berkeley.cs.jqf.examples;

public class GetModulatedFloat {
public static float getModulatedFloat(long value, long min, long max)
    {
        // Don't use max floats because when the active span is big compared to
        // the loop span the shader calculations would max out on the float
        // type. This caused incorrect alpha calculations and all features would
        // disappear.
        float modulatedValue = Float.MAX_VALUE / 2;
        if (value > min && value < max)
        {
            double range = max - min;
            double floatRange = modulatedValue - 0;

            double floatPerUnit = floatRange / range;
            long deltaFromMin = value - min;
            float floats = (float)(deltaFromMin * floatPerUnit);
            modulatedValue = floats;
        }
        else if (value <= min)
        {
            modulatedValue = 0;
        }

        return modulatedValue;
    }
public static void main(String[] args) {


getModulatedFloat(246948L, 684443L, 900124L);
}
}