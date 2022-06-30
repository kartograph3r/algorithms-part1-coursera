import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private int[][] grid;
    private int n;

    // Constructor
    public Board(int[][] tiles) {
        n = tiles.length;
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = tiles[i][j];
            }
        }
    }

    // String grid
    public String toString() {
        String gridString = Integer.toString(n);
        gridString += "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                gridString += String.valueOf(grid[i][j]);
            }
            gridString += "\n";
        }
        return gridString;
    }

    // Dimension of the grid
    public int dimensions() {
        return n;
    }

    // Number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != i * n + j + 1) hamming++;
            }
        }
        hamming--;
        return hamming;
    }

    // Manhattan sound fancy as fuck ngl
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tileNum = grid[i][j];
                // Break if 0
                if (tileNum == 0) break;
                // Calculate goal positions of a particular tile.
                int goali = (tileNum - 1) / n, goalj = (tileNum - 1) % n;
                manhattan += Math.abs(goali - i);
                manhattan += Math.abs(goalj - j);
            }
        }
        return manhattan;
    }

    // Is this a goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // Does it equal board y?
    public boolean equals(Object y) {
        // Check if null
        if (y == null) {
            return false;
        }
        // Check if y is a board type
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.dimensions() != that.dimensions()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.grid[i][j] != that.grid[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        // Scan for the location of blank tile (0)
        int x0 = 0, y0 = 0;
        boolean endLoop = false;
        for (int i = 0; i < n && !endLoop; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    x0 = i;
                    y0 = j;
                    endLoop = true;
                    break;
                }
            }
        }
        // Create a duplicate grid
        int[][] dupliGrid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dupliGrid[i][j] = grid[i][j];
            }
        }

        // Create a bag that we will return
        Bag<Board> neighbourBag = new Bag<Board>();

        // Swap up
        if (x0 > 0) {
            swapTiles(dupliGrid, x0, y0, 0, neighbourBag);
        }
        // Swap right
        if (y0 < n - 1) {
            swapTiles(dupliGrid, x0, y0, 1, neighbourBag);
        }
        // Swap down
        if (x0 < n - 1) {
            swapTiles(dupliGrid, x0, y0, 2, neighbourBag);
        }
        // Swap left
        if (y0 > 0) {
            swapTiles(dupliGrid, x0, y0, 3, neighbourBag);
        }

        return neighbourBag;
    }

    // Swap two tiles, direction: {0 = Up, 1 = Right, 2 = Down, 3 = Left}
    private void swapTiles(int[][] dupliGrid, int i, int j, int direction, Bag<Board> bag) {
        if (direction == 0) { // Swap up
            dupliGrid[i][j] = dupliGrid[i - 1][j];
            dupliGrid[i - 1][j] = 0;
            Board temp = new Board(dupliGrid);
            dupliGrid[i - 1][j] = dupliGrid[i][j];
            dupliGrid[i][j] = 0;
            bag.add(temp);
        }
        else if (direction == 1) { // Swap right
            dupliGrid[i][j] = dupliGrid[i][j + 1];
            dupliGrid[i][j + 1] = 0;
            Board temp = new Board(dupliGrid);
            dupliGrid[i][j + 1] = dupliGrid[i][j];
            dupliGrid[i][j] = 0;
            bag.add(temp);
        }
        else if (direction == 2) { // Swap down
            dupliGrid[i][j] = dupliGrid[i + 1][j];
            dupliGrid[i + 1][j] = 0;
            Board temp = new Board(dupliGrid);
            dupliGrid[i + 1][j] = dupliGrid[i][j];
            dupliGrid[i][j] = 0;
            bag.add(temp);
        }
        else if (direction == 3) { // Swap left
            dupliGrid[i][j] = dupliGrid[i][j - 1];
            dupliGrid[i][j - 1] = 0;
            Board temp = new Board(dupliGrid);
            dupliGrid[i][j - 1] = dupliGrid[i][j];
            dupliGrid[i][j] = 0;
            bag.add(temp);
        }
    }


    // Exchange an identical board with any two random boards exchanged.
    public Board twin() {
        int num1, num2, x1, y1, x2, y2;
        do {
            num1 = StdRandom.uniform(n * n);
            num2 = StdRandom.uniform(n * n);
            x1 = num1 / n;
            y1 = num1 % n;
            x2 = num2 / n;
            y2 = num2 % n;
        } while (num1 == num2 || grid[x1][y1] == 0 || grid[x2][y2] == 0);
        int[][] tempGrid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tempGrid[i][j] = grid[i][j];
            }
        }
        tempGrid[x1][y1] = grid[x2][y2];
        tempGrid[x2][y2] = grid[x1][y1];
        Board twin = new Board(tempGrid);
        return twin;
    }


    public static void main(String[] args) {
        int[][] test = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                test[i][j] = i * 3 + j + 1;
            }
            test[2][2] = 0;
        }
        Board lol = new Board(test);
        for (Board s : lol.neighbours()) {
            String temp = s.toString();
            StdOut.println(temp);
        }
    }

}
