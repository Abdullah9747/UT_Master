package edu.berkeley.cs.jqf.examples;

public class ConvertBurstSizeToKiloBits {
public static long convertBurstSizeToKiloBits(long burstSizeInPackets, long packetSizeInBytes) {
        return (burstSizeInPackets * packetSizeInBytes * 8) / 1024L;
    }
public static void main(String[] args) {


convertBurstSizeToKiloBits(465627L, 775580L);
}
}