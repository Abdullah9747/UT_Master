package dev.fuzzit.examplejava;



public class calculateGridPaths {
    public static void calculateGridPaths(int rows, int cols, int[][] obstacles) {
        if (rows <= 0 || cols <= 0) {
            System.out.println("Grid dimensions must be positive.");
            return;
        }

        int[][] dp = new int[rows][cols];

        // Initialize the starting position
        dp[0][0] = obstacles[0][0] == 1 ? 0 : 1;

        // Fill the first row
        for (int col = 1; col < cols; col++) {
            dp[0][col] = (obstacles[0][col] == 1 || dp[0][col - 1] == 0) ? 0 : 1;
        }

        // Fill the first column
        for (int row = 1; row < rows; row++) {
            dp[row][0] = (obstacles[row][0] == 1 || dp[row - 1][0] == 0) ? 0 : 1;
        }

        // Fill the rest of the grid
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                if (obstacles[row][col] == 1) {
                    dp[row][col] = 0; // Obstacle blocks the path
                } else {
                    dp[row][col] = dp[row - 1][col] + dp[row][col - 1];
                }
            }
        }

        System.out.println(dp[rows - 1][cols - 1]);
    }


    
public static void main(String[] args) {
int[][] obstacles = {{93,60},{52,35}};

calculateGridPaths(2, 2, obstacles);
}
}