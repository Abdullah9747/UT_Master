package demo; import gov.nasa.jpf.symbc.Debug;public class CalculateWaterUsage {
    public static double calculateWaterUsage(int familyMembers, int appliances, boolean hasGarden, int dailyUseLiters) {
        double baseUsage = familyMembers * dailyUseLiters;
        if (hasGarden) {
            baseUsage += 50; // Additional for garden
        }
        if (appliances > 0) {
            baseUsage += appliances * 10; // Additional for appliances
        }
        if (familyMembers > 5) {
            baseUsage *= 1.1; // Slight increase for larger families
        }
        return baseUsage;
    }

public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
int x1 = Debug.makeSymbolicInteger("x1");
boolean x2 = Debug.makeSymbolicBoolean("x2");
int x3 = Debug.makeSymbolicInteger("x3");
calculateWaterUsage(x0,x1,x2,x3);
}}