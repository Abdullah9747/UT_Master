package dev.fuzzit.examplejava;

import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class WaterUsageTest {

      @Fuzz
      public void fuzz(int familyMembers, int appliances, boolean hasGarden, int dailyUseLiters) {
            // // Log the fuzzed input values
            // System.out.println("Fuzzed Input Values: " +
            // "familyMembers = " + familyMembers + ", " +
            // "appliances = " + appliances + ", " +
            // "hasGarden = " + hasGarden + ", " +
            // "dailyUseLiters = " + dailyUseLiters);

            // // **Ensure output is flushed**
            // System.out.flush();

            // Execute the actual test logic.
            WaterUsage.calculateWaterUsage(familyMembers, appliances, hasGarden, dailyUseLiters);
      }
}
