public class BubbleGrid {
    private int[][] grid;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    private int[][] helperInit(int[][] darts) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] newGrid = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                newGrid[i][j] = this.grid[i][j];
            }
        }
        for (int i = 0; i < darts.length; i++) {
            int x = darts[i][0];
            int y = darts[i][1];
            if (newGrid[x][y] == 1) {
                newGrid[x][y] = 2;
            }
        }
        return newGrid;
    }
    private void helperInitUnionFind(int[][] darts, int[][] newGrid, UnionFind uf) {
        int row = newGrid.length;
        int col = newGrid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (newGrid[i][j] == 1) {
                    unionAll(i, j, uf, newGrid);
                }
            }
        }
    }

    private void unionAll(int x, int y, UnionFind uf, int[][] newGrid) {
        // you have to assure that newGrid[x][y] == 1
        assert newGrid[x][y] == 1;
        int row = newGrid.length;
        int col = newGrid[0].length;
        int[][] coordinates = {{x - 1, y}, {x + 1, y}, {x, y + 1}, {x, y - 1}};
        for (int i = 0; i < coordinates.length; ++i) {
            int newX = coordinates[i][0];
            int newY = coordinates[i][1];
            if ((newX >= 0) && (newX <= row - 1) && (newY >= 0) && (newY <= col - 1) && (newGrid[newX][newY] == 1)) {
                uf.union( x * col + y + 1, newX * col + newY + 1);
            }
        }
        if (x == 0) {
            uf.union(0, y + 1);
        }
    }

    /**
     * there are some points we have to pay attention to in this problem
     * the first thing is to make use of helper method
     * the second thing is to make it clear which one is the vertical one and which one
     * is the horizontal one
     * the third thing which is the most hard one is that when doing iteration, you have
     * to reverse the whole order which is from length -1 to 0
     * ponder it!
     * @param darts
     * @return
     */
    public int[] popBubbles(int[][] darts) {
        // TODO
        int[] numberOfBubbles = new int[darts.length];
        int row = grid.length;
        int col = grid[0].length;
        UnionFind unionFind = new UnionFind(row * col + 1);
        int[][] newGrid = helperInit(darts); // initialize a newGrid
        helperInitUnionFind(darts, newGrid, unionFind);

        for (int i = darts.length - 1; i >= 0 ; i--) {
            int x = darts[i][0];
            int y = darts[i][1];
            int[][] coordinates = {{x - 1, y}, {x + 1, y}, {x, y + 1}, {x, y - 1}};
            if (newGrid[x][y] == 2) {
                int Number = unionFind.sizeOf(0);
                newGrid[x][y] = 1;
                unionAll(x, y, unionFind, newGrid);
                int newNumber = unionFind.sizeOf(0);
                if (newNumber > Number) {
                    numberOfBubbles[i] = newNumber - Number - 1;
                } else {
                    numberOfBubbles[i] = 0;
                }
            } else {
                numberOfBubbles[i] = 0;
            }
        }
        return numberOfBubbles;
    }
    public static void main(String[] args) {
        int[][] grid = {{1,0,0,0},{1,1,0,0}};
        BubbleGrid mygrid = new BubbleGrid(grid);
        int[][] hits = {{1,1},{1,0}};
        int[] ans = mygrid.popBubbles(hits);
        for (int i = 0; i < ans.length; i++) {
            System.out.println(ans[i]);
        }
    }
}