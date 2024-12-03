package dev.fuzzit.examplejava;

public class isEven {
    public static void isEven(int num) {
        boolean result = (num % 2 == 0);
        System.out.println(result);
    }

 

    public static void main(String[] args) {
   
        isEven(9);
      
    }
}