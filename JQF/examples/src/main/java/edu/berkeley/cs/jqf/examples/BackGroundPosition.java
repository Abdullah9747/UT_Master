package edu.berkeley.cs.jqf.examples;

public class BackGroundPosition {
protected static String backGroundPosition(int clipX, int clipY) {
        return "background-position: " + clipX + "px " + clipY + "px !important";
    }
public static void main(String[] args) {


backGroundPosition(38, 42);
}
}