package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    private int[][] tiles;
    public Board(int[][] tiles) {
        int N = tiles.length;
        this.tiles = new int[N][N];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                this.tiles[row][col] = tiles[row][col];
            }
        }
    }
    public int tileAt(int i, int j) {
        if (i >= 0 && i < size() && j >=0 && j < size()) {
            return tiles[i][j];
        }
        throw new IndexOutOfBoundsException();

    }
    public int size() {
        return tiles.length;
    }
    public int hamming() {
        int hammingDistance = 0;
        for (int row = 0; row < size(); row++) {
            for (int col = 0; col < size(); col++) {
                if (tileAt(row, col) == 0) {
                    continue;
                }
                if (tileAt(row, col) != row * size() + col + 1) {
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    public int manhattan() {
        int estimatedDistance = 0;
        for (int r = 0; r < size(); r += 1) {
            for (int c = 0; c < size(); c += 1) {
                int actualValue = tileAt(r, c);
                if (actualValue == 0) { // Continue when hit the BLANK tile
                    continue;
                }
                int expectedRow = (actualValue - 1) / size();
                int expectedColumn = (actualValue - 1) % size();
                estimatedDistance += Math.abs(expectedRow - r);
                estimatedDistance += Math.abs(expectedColumn - c);
            }
        }
        return estimatedDistance;
    }


    @Override
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (!y.getClass().equals(this.getClass())) {
            return false;
        }
        Board that = (Board) y;
        if (this.size() != that.size()) {
            return false;
        }
        for (int row = 0; row < size(); row++) {
            for (int col = 0; col < size(); col++) {
                if (this.tileAt(row, col) != that.tileAt(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }



    @Override
    public int estimatedDistanceToGoal() {
        return this.manhattan();
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }


}
