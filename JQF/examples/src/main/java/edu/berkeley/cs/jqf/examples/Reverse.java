package edu.berkeley.cs.jqf.examples;

public class Reverse {
public static int reverse(int x) {
        String str = String.valueOf(x);
        char[] chars = str.toCharArray();
        char tmp;
        int left = 0;
        int right = chars.length - 1;
        int start = 0;
        int flag = 1;
        if (chars[0] == '-') {
            left = 1;
            start = 1;
            flag = -1;
        }
        while (left < right) {
            tmp = chars[left];
            chars[left] = chars[right];
            chars[right] = tmp;
            left++;
            right--;
        }
        long resultLong = 0;
        for (; start < chars.length; start++) {
            resultLong = resultLong * 10 + (chars[start] - 48);
        }
        resultLong *= flag;
        if (resultLong > Integer.MAX_VALUE || resultLong < Integer.MIN_VALUE) {
            return 0;
        } else {
            return (int) resultLong;
        }
    }
public static void main(String[] args) {


reverse(40);
}
}