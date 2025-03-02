package edu.berkeley.cs.jqf.examples;

public class IntToBytes {
public static byte[] intToBytes(int i) {
    return new byte[]{(byte) ((i >> 24) & 0xFF), (byte) ((i >> 16) & 0xFF),
        (byte) ((i >> 8) & 0xFF),
        (byte) (i & 0xFF)};
  }
public static void main(String[] args) {


intToBytes(74);
}
}