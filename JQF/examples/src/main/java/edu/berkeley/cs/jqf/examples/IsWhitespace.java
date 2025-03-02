package edu.berkeley.cs.jqf.examples;

public class IsWhitespace {
public static boolean isWhitespace(int c){
        return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r';
    }
public static void main(String[] args) {


isWhitespace(1);
}
}