package demo;

import gov.nasa.jpf.symbc.Debug;

public class NumericExample {
    // Static counter to track exploration level
    private static int level = 0;

    public static void test(int a, int b) {
        level++;
        System.out.println("BFS Level " + level + ": Exploring test(a=" + a + ", b=" + b + ")");
        
        try {
            // Simplified division check
            if (b == 0) {
                System.out.println("Level " + level + ": Path - Division by zero check");
                return;
            }
            
            int c = a/b;
            System.out.println("Level " + level + ": Computed c = " + c);
            
            // Simple branching structure for BFS visualization
            if (c > 0) {
                System.out.println("Level " + level + ": Path - c > 0");
                if (a > b) {
                    System.out.println("Level " + level + ": Path - a > b");
                    test1(a, b);
                } else {
                    System.out.println("Level " + level + ": Path - a <= b");
                    processValue(c);
                }
            } else {
                System.out.println("Level " + level + ": Path - c <= 0");
                if (a < b) {
                    System.out.println("Level " + level + ": Path - a < b");
                    test1(b, a);
                }
            }
            
        } catch (ArithmeticException e) {
            System.out.println("Level " + level + ": Exception Path - " + e.getMessage());
        } finally {
            level--;
        }
    }
    
    private static void processValue(int value) {
        level++;
        System.out.println("Level " + level + ": ProcessValue(" + value + ")");
        if (value > 0) {
            System.out.println("Level " + level + ": ProcessValue - Positive path");
        } else {
            System.out.println("Level " + level + ": ProcessValue - Non-positive path");
        }
        level--;
    }
    
    public static void test1(int a, int b) {
        level++;
        System.out.println("Level " + level + ": test1(a=" + a + ", b=" + b + ")");
        
        try {
            if (a + b == 0) {
                System.out.println("Level " + level + ": Path - Zero sum check");
                return;
            }
            
            if (a > b) {
                System.out.println("Level " + level + ": Path - a > b");
                processValue(a - b);
            } else {
                System.out.println("Level " + level + ": Path - a <= b");
                processValue(b - a);
            }
            
        } catch (ArithmeticException e) {
            System.out.println("Level " + level + ": Exception Path - " + e.getMessage());
        } finally {
            level--;
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting BFS Exploration");
        
        // Create symbolic variables
        int x = Debug.makeSymbolicInteger("x");
        int y = Debug.makeSymbolicInteger("y");
        
        // Simple test cases for BFS visualization
        System.out.println("\nFirst Level Exploration");
        test(x, y);
        
        System.out.println("\nSecond Level Exploration");
        test1(x, y);
        
        System.out.println("\nThird Level Exploration");
        if (x > y) {
            System.out.println("Branch 1: x > y");
            test(x, y);
        } else {
            System.out.println("Branch 2: x <= y");
            test1(y, x);
        }
    }
}