package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class ConvertBurstSizeToKiloBitsTest{ 

@Fuzz
public void fuzz(long burstSizeInPackets,long packetSizeInBytes){
      ConvertBurstSizeToKiloBits.convertBurstSizeToKiloBits(burstSizeInPackets,packetSizeInBytes);
}
}