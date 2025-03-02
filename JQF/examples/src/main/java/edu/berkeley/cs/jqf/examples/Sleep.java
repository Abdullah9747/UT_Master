package edu.berkeley.cs.jqf.examples;

public class Sleep {
public static void sleep (long millis) {
        try {
            Thread.sleep (millis);
        } catch (InterruptedException ignored) { }
    }
public static void main(String[] args) {


sleep(522317L);
}
}