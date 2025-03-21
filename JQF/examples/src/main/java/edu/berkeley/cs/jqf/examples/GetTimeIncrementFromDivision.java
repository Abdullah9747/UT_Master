package edu.berkeley.cs.jqf.examples;

public class GetTimeIncrementFromDivision {
public static String getTimeIncrementFromDivision( long nanosecondCount ) {
        String timeUnit = "second increment";
        if ( nanosecondCount >= 24l * 60l * 60l * 1000l * 1000l * 1000l ) {
            timeUnit = nanosecondCount / ( 24l * 60l * 60l * 1000l * 1000l * 1000l ) + " day increment";
        } else if ( nanosecondCount >= 60l * 60l * 1000l * 1000l * 1000l ) {
            timeUnit = nanosecondCount / ( 60l * 60l * 1000l * 1000l * 1000l ) + " hour increment";
        } else if ( nanosecondCount >= 60l * 1000l * 1000l * 1000l ) {
            timeUnit = nanosecondCount / ( 60l * 1000l * 1000l * 1000l ) + " minute increment";
        } else if ( nanosecondCount >= 1000l * 1000l * 1000l ) {
            timeUnit = nanosecondCount / ( 1000l * 1000l * 1000l ) + " second increment";
        } else if ( nanosecondCount >= 1000l * 1000l ) {
            timeUnit = nanosecondCount / ( 1000l * 1000l ) + " millisecond increment";
        } else if ( nanosecondCount >= 1000l ) {
            timeUnit = nanosecondCount / 1000l + " microsecond increment";
        } else {
            timeUnit = nanosecondCount + " nanosecond increment";
        }
        return timeUnit;
    }
public static void main(String[] args) {


getTimeIncrementFromDivision(340203L);
}
}