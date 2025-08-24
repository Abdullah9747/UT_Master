package demo; import gov.nasa.jpf.symbc.Debug;public class Sqrt {
public static int sqrt(int x) {
		if (x < 0)
			throw new IllegalArgumentException("Square root of negative number");
		int y = 0;
		for (int i = 1 << 15; i != 0; i >>>= 1) {
			y |= i;
			if (y > 46340 || y * y > x)
				y ^= i;
		}
		return y;
	}
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
sqrt(x0);
}}