package edu.berkeley.cs.jqf.examples;

public class GetUnsignedAlignedValue {
public static long getUnsignedAlignedValue(long unsignedValue, long alignment) {
		if (alignment == 0 || unsignedValue % alignment == 0) {
			return unsignedValue;
		}
		boolean negative = unsignedValue < 0;
		if (negative) {
			unsignedValue = -(unsignedValue + alignment);
		}
		long alignedValue = ((unsignedValue + alignment - 1) / alignment) * alignment;
		if (negative) {
			alignedValue = -alignedValue;
		}
		return alignedValue;
	}
public static void main(String[] args) {


getUnsignedAlignedValue(203964L, 321564L);
}
}