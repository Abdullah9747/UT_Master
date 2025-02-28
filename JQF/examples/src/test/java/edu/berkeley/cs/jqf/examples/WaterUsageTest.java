package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class WaterUsageTest{ 

@Fuzz
public void fuzz(int familyMembers,int appliances,boolean hasGarden,int dailyUseLiters){
      WaterUsage.calculateWaterUsage(familyMembers,appliances,hasGarden,dailyUseLiters);
}
}