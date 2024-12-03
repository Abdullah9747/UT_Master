package dev.fuzzit.examplejava;

public class findMax {
    public static void findMax(int[] arr) {
        int max = arr[0];
        for(int i = 1; i < arr.length; i++) {
            if(arr[i] > max) max = arr[i];
        }
        System.out.println(max);
    }


    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        findMax(arr);
    }
}