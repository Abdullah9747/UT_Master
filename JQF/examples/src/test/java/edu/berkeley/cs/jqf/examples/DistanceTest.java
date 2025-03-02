package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class DistanceTest{ 

@Fuzz
public void fuzz(double lat1,double lat2,double lon1,double lon2){
      Distance.distance(lat1,lat2,lon1,lon2);
}
}