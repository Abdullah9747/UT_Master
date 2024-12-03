package dev.fuzzit.examplejava;

public class hoursToMinutes {
    public static void hoursToMinutes(int hours) {
        int minutes = hours * 60;
        System.out.println(minutes);
    }

   

    public static void main(String[] args) {
        hoursToMinutes(5);
    }
}