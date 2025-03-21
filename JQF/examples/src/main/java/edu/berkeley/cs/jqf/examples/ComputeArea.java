package edu.berkeley.cs.jqf.examples;

public class ComputeArea {
public static int computeArea(int A, int B,
                           int C, int D,
                           int E, int F,
                           int G, int H) {
        int areaSum = (C-A)*(D-B) + (G-E)*(H-F);

        if(E>=C || A>=G || B>=H || F>=D){
            return areaSum;
        }
        int bX = Math.max(A, E);
        int bY = Math.max(B, F);
        int tX = Math.min(C, G);
        int tY = Math.min(D, H);
        int areaIntersect = (tX-bX)*(tY-bY);

        return areaSum - areaIntersect;

    }
public static void main(String[] args) {


computeArea(55, 91, 63, 1, 25, 62, 34, 28);
}
}