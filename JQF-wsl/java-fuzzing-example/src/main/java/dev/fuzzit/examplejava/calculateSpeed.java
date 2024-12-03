package dev.fuzzit.examplejava;

public class calculateSpeed {
    public static void calculateSpeed(int distance, int time) {
        int speed = distance / time;
        System.out.println(speed);
    }

   

    public static void main(String[] args) {
        
        calculateSpeed(100, 80);
    }
}