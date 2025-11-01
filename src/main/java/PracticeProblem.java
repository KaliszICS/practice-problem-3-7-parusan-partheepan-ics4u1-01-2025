import java.util.*;

public class PracticeProblem {
    
    /**
     * Searches a maze to find the minimum number of moves from start (S) to finish (F)
     * @param maze 2D String array representing the maze
     * @return minimum number of moves, or -1 if no path exists
     */
    public static int searchMazeMoves(String[][] maze) {
        if (maze == null || maze.length == 0 || maze[0].length == 0) {
            return -1;
        }
        
        int rows = maze.length;
        int cols = maze[0].length;
        
        // Find start position (bottom left - last row, first column)
        int startRow = rows - 1;
        int startCol = 0;
        
        // Verify start position
        if (!maze[startRow][startCol].equals("S")) {
            // Search for S if not at expected position
            boolean found = false;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (maze[i][j].equals("S")) {
                        startRow = i;
                        startCol = j;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (!found) return -1;
        }
        
        // BFS to find shortest path
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        
        // Add start position: {row, col, moves}
        queue.offer(new int[]{startRow, startCol, 0});
        visited[startRow][startCol] = true;
        
        // Directions: up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int moves = current[2];
            
            // Check if we reached the finish
            if (maze[row][col].equals("F")) {
                return moves;
            }
            
            // Explore all four directions
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // Check bounds
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    // Check if not visited, not a wall
                    if (!visited[newRow][newCol] && !maze[newRow][newCol].equals("*")) {
                        visited[newRow][newCol] = true;
                        queue.offer(new int[]{newRow, newCol, moves + 1});
                    }
                }
            }
        }
        
        // No path found
        return -1;
    }
    
    // Test method
    public static void main(String[] args) {
        // Test case 1: Simple maze
        String[][] maze1 = {
            {" ", " ", "F"},
            {" ", "*", " "},
            {"S", " ", " "}
        };
        System.out.println("Test 1 - Expected: 4, Got: " + searchMazeMoves(maze1));
        
        // Test case 2: Direct path
        String[][] maze2 = {
            {" ", " ", "F"},
            {"S", " ", " "}
        };
        System.out.println("Test 2 - Expected: 3, Got: " + searchMazeMoves(maze2));
        
        // Test case 3: No path
        String[][] maze3 = {
            {" ", " ", "F"},
            {"*", "*", "*"},
            {"S", " ", " "}
        };
        System.out.println("Test 3 - Expected: -1, Got: " + searchMazeMoves(maze3));
        
        // Test case 4: Larger maze
        String[][] maze4 = {
            {" ", "*", " ", "F"},
            {" ", "*", " ", " "},
            {" ", " ", " ", "*"},
            {"S", "*", " ", " "}
        };
        System.out.println("Test 4 - Expected: 6, Got: " + searchMazeMoves(maze4));
    }
}