package edu.berkeley.cs.jqf.examples;

public class CheckCategoryMatchingMainCategory {
public static boolean checkCategoryMatchingMainCategory(int cat, int possibleMainCat) {
        return possibleMainCat % 1000 == 0 && cat / 1000 == possibleMainCat / 1000;
    }
public static void main(String[] args) {


checkCategoryMatchingMainCategory(65, 37);
}
}