/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    private WeightedQuickUnionUF grid; // stores root of a particular cell
    private boolean[] shadowGrid; // stores whether a cell is open or not
    private int size; // stores the size of the grid
    private int openSites = 0; // number of open sites
    private int virtualTopElement;
    private int virtualBottomElement;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Size of grid must be positive!");
        }
        else {
            grid = new WeightedQuickUnionUF(n * n + 2);
            shadowGrid = new boolean[n * n + 2];
            size = n;
            virtualTopElement = size * size;
            virtualBottomElement = size * size + 1;
            shadowGrid[virtualTopElement] = true; // top virtual element
            shadowGrid[virtualBottomElement] = true; // bottom virtual element
            for (int i = 0; i < size; i++) {
                grid.union(i, virtualTopElement); // Union the top virtual cell and the top row
                grid.union((size - 1) * size + i,
                           virtualBottomElement); // Union bottom virtual cell and bottom row
            }

        }

    }

    private int helper(int row, int col) {
        return ((row - 1) * size + col - 1);
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException("Arguments must be between [1,n]");
        }
        if (!isOpen(row, col)) {
            int cell = helper(row, col);
            shadowGrid[cell] = true;
            openSites++;
            // Union cell with neighbours
            // Upper neighbour
            if (row > 1 && isOpen(row - 1, col)) {
                grid.union(cell, cell - size);
            }
            // Left neighbour
            if (col > 1 && isOpen(row, col - 1)) {
                grid.union(cell, cell - 1);
            }
            // Right neighbour
            if (col < size && isOpen(row, col + 1)) {
                grid.union(cell, cell + 1);
            }
            // Lower neighbour
            if (row < size && isOpen(row + 1, col)) {
                grid.union(cell, cell + size);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException("Arguments must be between [1,n]");
        }
        return shadowGrid[helper(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException("Arguments must be between [1,n]");
        }
        else if (!isOpen(row, col)) {
            return false;
        }
        else {
            return grid.find(helper(row, col)) == grid.find(virtualTopElement);
        }
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        if (size == 1) {
            return isOpen(1, 1);
        }
        return grid.find(virtualTopElement) == grid.find(virtualBottomElement);
    }

    public static void main(String[] args) {
        // Empty on purpose
    }
}
