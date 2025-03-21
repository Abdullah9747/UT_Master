package edu.berkeley.cs.jqf.examples;

public class ReciprocalMod {
public static int reciprocalMod(int x, int m) {
		if (!(0 <= x && x < m))
			throw new IllegalArgumentException();
		
		// Based on a simplification of the extended Euclidean algorithm
		int y = x;
		x = m;
		int a = 0;
		int b = 1;
		while (y != 0) {
			int z = x % y;
			int c = a - x / y * b;
			x = y;
			y = z;
			a = b;
			b = c;
		}
		if (x == 1)
			return a >= 0 ? a : a + m;
		else
			throw new IllegalArgumentException("Reciprocal does not exist");
	}
public static void main(String[] args) {


reciprocalMod(27, 28);
}
}