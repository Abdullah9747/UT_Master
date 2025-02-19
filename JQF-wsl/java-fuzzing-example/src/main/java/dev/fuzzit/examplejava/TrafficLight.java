package dev.fuzzit.examplejava;

public class TrafficLight {
    public static int calculateGreenLightTime(int roadLength, int trafficDensity, int avgSpeed, boolean isPeakHour) {
        if (roadLength <= 0 || trafficDensity <= 0 || avgSpeed <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        int baseTime = roadLength / avgSpeed; // Basic green light time
        if (isPeakHour) {
            baseTime += (trafficDensity / 10); // Increase time during peak hours
        } else if (trafficDensity > 50) {
            baseTime += (trafficDensity / 20); // Increase time for higher density
        } else {
            baseTime = Math.max(baseTime - 5, 10); // Minimum time during low density
        }
        return baseTime;
    }

public static void main(String[] args) {


calculateGreenLightTime(40, 55, 92, true);
}
}