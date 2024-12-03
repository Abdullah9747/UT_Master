package dev.fuzzit.examplejava;

public class celsiusToFahrenheit {
    public static void celsiusToFahrenheit(int celsius) {
        int fahrenheit = (celsius * 9/5) + 32;
        System.out.println(fahrenheit);
    }



    public static void main(String[] args) {
        
        celsiusToFahrenheit(30);

    }
}