package edu.berkeley.cs.jqf.examples;

public class IsCorrectFull {
public static boolean isCorrectFull(long fetchedLength, long contentLength) {
        return fetchedLength == contentLength;
    }
public static void main(String[] args) {


isCorrectFull(346883L, 683305L);
}
}