package demo; import gov.nasa.jpf.symbc.Debug;public class IsPrime {
public static boolean isPrime(int x) {
        if (x <= 1) {
            return false;
        }

        int i = 2;
        while (i <= x && x % i != 0) {
            i++;
        }
        return i == x;
    }
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
isPrime(x0);
}}