package demo;

import gov.nasa.jpf.symbc.Debug;

public class testing {
    public static int testing(int sym) {
        if (sym > 0) {
            return sym * sym;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        int x = Debug.makeSymbolicInteger("x");
        int result = testing(x);
    }
}
