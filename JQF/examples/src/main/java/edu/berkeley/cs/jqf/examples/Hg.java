package edu.berkeley.cs.jqf.examples;

public class Hg {
public static double hg(long D, long gt, long S) {

		double dgt = 0;
		for (int i = 1; i <= D - gt; i++) {
			dgt += Math.log(i);
		}

		double ds = 0;
		for (int i = 1; i <= D - S; i++) {
			ds += Math.log(i);
		}

		double dgts = 0;
		for (int i = 1; i <= D - gt - S; i++) {
			dgts += Math.log(i);
		}

		double d = 0;
		for (int i = 1; i <= D; i++) {
			d += Math.log(i);
		}

		double P = Math.exp(dgt + ds - dgts - d);

		return P;
	}
public static void main(String[] args) {


hg(888156L, 330064L, 303479L);
}
}