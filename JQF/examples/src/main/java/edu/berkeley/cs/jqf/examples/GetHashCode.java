package edu.berkeley.cs.jqf.examples;

public class GetHashCode {
public static int getHashCode(boolean val)
    {
        return val ? 1231 : 1237;
    }
public static void main(String[] args) {


getHashCode(false);
}
}