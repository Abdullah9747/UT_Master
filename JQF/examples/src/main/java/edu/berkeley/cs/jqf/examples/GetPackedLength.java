package edu.berkeley.cs.jqf.examples;

public class GetPackedLength {
public static int getPackedLength(int nDataUnits)
    {
        return (nDataUnits + 1) / 2;
    }
public static void main(String[] args) {


getPackedLength(70);
}
}