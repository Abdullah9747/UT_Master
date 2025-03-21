package edu.berkeley.cs.jqf.examples;

public class Build_lenenc_int {
public static byte[] build_lenenc_int(long value) {
        byte[] packet = null;
        if (value == 251) {
            packet = new byte[1];
            packet[0] = (byte) 0xFB;
        } else if (value < 251) {
            packet = new byte[1];
            packet[0] = (byte) (value & 0xFF);
        } else if (value < 65535) {
            packet = new byte[3];
            packet[0] = (byte) 0xFC;
            packet[1] = (byte) (value & 0xFF);
            packet[2] = (byte) ((value >> 8) & 0xFF);
        } else if (value < 16777215) {
            packet = new byte[4];
            packet[0] = (byte) 0xFD;
            packet[1] = (byte) (value & 0xFF);
            packet[2] = (byte) ((value >> 8) & 0xFF);
            packet[3] = (byte) ((value >> 16) & 0xFF);
        } else {
            packet = new byte[9];
            packet[0] = (byte) 0xFE;
            packet[1] = (byte) (value & 0xFF);
            packet[2] = (byte) ((value >> 8) & 0xFF);
            packet[3] = (byte) ((value >> 16) & 0xFF);
            packet[4] = (byte) ((value >> 24) & 0xFF);
            packet[5] = (byte) ((value >> 32) & 0xFF);
            packet[6] = (byte) ((value >> 40) & 0xFF);
            packet[7] = (byte) ((value >> 48) & 0xFF);
            packet[8] = (byte) ((value >> 56) & 0xFF);
        }

        return packet;
    }
public static void main(String[] args) {


build_lenenc_int(663190L);
}
}