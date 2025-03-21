package edu.berkeley.cs.jqf.examples;

public class ComputeMaximumAllowedEdges {
static <V, E> int computeMaximumAllowedEdges(
        int n, boolean isDirected, boolean createLoops, boolean createMultipleEdges)
    {
        if (n == 0) {
            return 0;
        }

        int maxAllowedEdges;
        try {
            if (isDirected) {
                maxAllowedEdges = Math.multiplyExact(n, n - 1);
            } else {
                // assume undirected
                if (n % 2 == 0) {
                    maxAllowedEdges = Math.multiplyExact(n / 2, n - 1);
                } else {
                    maxAllowedEdges = Math.multiplyExact(n, (n - 1) / 2);
                }
            }

            if (createLoops) {
                if (createMultipleEdges) {
                    return Integer.MAX_VALUE;
                } else {
                    if (isDirected) {
                        maxAllowedEdges = Math.addExact(maxAllowedEdges, Math.multiplyExact(2, n));
                    } else {
                        // assume undirected
                        maxAllowedEdges = Math.addExact(maxAllowedEdges, n);
                    }
                }
            } else {
                if (createMultipleEdges) {
                    if (n > 1) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
        } catch (ArithmeticException e) {
            return Integer.MAX_VALUE;
        }
        return maxAllowedEdges;
    }
public static void main(String[] args) {


computeMaximumAllowedEdges(21, true, true, true);
}
}