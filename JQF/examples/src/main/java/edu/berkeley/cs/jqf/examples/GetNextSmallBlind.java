package edu.berkeley.cs.jqf.examples;

public class GetNextSmallBlind {
public static int getNextSmallBlind(int maxSmallBlind)
    {
        if (maxSmallBlind == 0)
        {
            return 100;
        }
        int result = maxSmallBlind;
        int exp = 0;
        while(result % 10 == 0)
        {
            exp++;
            result /= 10;
        }
        if (result == 2)
        {
            result = 5;
        }
        else
        {
            result *= 2;
        }
        while(exp > 0)
        {
            exp--;
            result *= 10;
        }
        return result;
    }
public static void main(String[] args) {


getNextSmallBlind(44);
}
}