package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private WeightedQuickUnionUF uf;
    private boolean[][] cache;
    private int openCount = 0;
    /**
     * create N-by-N grid, with all sites initially blocked
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.openCount = 0;
        this.N = N;
        uf = new WeightedQuickUnionUF(N * N + 2);
        cache = new boolean[N][N];
        for (int i = 1; i <= N; ++i) {
            uf.union(0, i);
        }
    }

    private int xyTo1D(int row, int col) {
        int number = row * N + col;
        return number + 1;
    }
    private void throwException(int row, int col) {
        if (row >= 0 && row < N && col >= 0 && col < N) {
            return;
        }
        throw new IndexOutOfBoundsException();
    }
    private boolean validate(int row, int col) {
        boolean condition = row >= 0 && row < N && col >= 0 && col < N;
        return condition;
    }
    private boolean isLastRow(int row, int col) {
        return row == N - 1 && col > 0 && col < N;
    }
    private void unionAll(int row, int col) {
        if (isLastRow(row, col)) {
            uf.union(xyTo1D(row, col), N * N - 1);
        }
        int[][] toBeUnioned = {{row - 1, col}, {row + 1, col}, {row, col + 1}, {row, col - 1}};
        for (int i = 0; i < toBeUnioned.length; i++) {
            int x = toBeUnioned[i][0];
            int y = toBeUnioned[i][1];
            if (validate(x, y) && isOpen(x, y)) {
                uf.union(xyTo1D(row, col), xyTo1D(x, y));
                if (isLastRow(x, y)) {
                    uf.union(xyTo1D(x, y), N * N - 1);
                }
            }
        }
    }

    /**
     * open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        cache[row][col] = true;
        unionAll(row, col);
        openCount++;
    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        return cache[row][col];
    }

    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        return uf.connected(xyTo1D(row, col), 0);
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return openCount;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return uf.connected(0, N * N + 1);
    }
    public static void main(String[] args) {

    }


}
