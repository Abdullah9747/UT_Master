package dev.fuzzit.examplejava;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class TrafficLightTest{ 

@Fuzz
public void fuzz(int roadLength,int trafficDensity,int avgSpeed,boolean isPeakHour){
      TrafficLight.calculateGreenLightTime(roadLength,trafficDensity,avgSpeed,isPeakHour);
}
}