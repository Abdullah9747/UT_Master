package edu.berkeley.cs.jqf.examples;

public class Convert {
public static String convert(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Argument is not positive");
        }
        StringBuilder builder = new StringBuilder();
        while (n > 0) {
            n--;
            int remainder = n % 26;
            n /= 26;
            builder.append((char) (remainder + 65));
        }
        return builder.reverse().toString();
    }
public static void main(String[] args) {


convert(54);
}
}