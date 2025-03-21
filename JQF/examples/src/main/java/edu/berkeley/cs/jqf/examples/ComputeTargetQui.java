package edu.berkeley.cs.jqf.examples;

public class ComputeTargetQui {
public static double computeTargetQui(boolean implicit, double value, double currentValue) {
    // We want Qui to change based on value. What's the target value, Qui'?
    if (implicit) {
      // Target is really 1, or 0, depending on whether value is positive or negative.
      // This wouldn't account for the strength though. Instead the target is a function
      // of the current value and strength. If the current value is c, and value is positive
      // then the target is somewhere between c and 1 depending on the strength. If current
      // value is already >= 1, there's no effect. Similarly for negative values.
      if (value > 0.0f && currentValue < 1.0) {
        double diff = 1.0 - Math.max(0.0, currentValue);
        return currentValue + (value / (1.0 + value)) * diff;
      }
      if (value < 0.0f && currentValue > 0.0) {
        double diff = -Math.min(1.0, currentValue);
        return currentValue + (value / (value - 1.0)) * diff;
      }
      // No change
      return Double.NaN;
    } else {
      // Non-implicit -- value is supposed to be the new value
      return value;
    }
  }
public static void main(String[] args) {


computeTargetQui(true, 13.64893099620701, 1.2541291451256398);
}
}