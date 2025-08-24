package demo; import gov.nasa.jpf.symbc.Debug;public class IntLog2 {
public static int intLog2(long num) {
        long l = Double.doubleToRawLongBits(num);
        return (int) ((l >> 52) - 1023);
    }
public static void main(String[] args){
long x0 = Debug.makeSymbolicLong("x0");
intLog2(x0);
}}