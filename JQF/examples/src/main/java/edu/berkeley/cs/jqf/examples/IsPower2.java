package edu.berkeley.cs.jqf.examples;

public class IsPower2 {
public static boolean isPower2(long value) {
        return value == 1 || value == 2 || value == 4 || value == 8 || value == 16 ||
                value == 32 || value == 64 || value == 128 || value == 256 ||
                value == 512 || value == 1024 || value == 2048 || value == 4096;
    }
public static void main(String[] args) {


isPower2(331805L);
}
}