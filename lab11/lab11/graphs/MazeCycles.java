package lab11.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private boolean cycleFound = false;
    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }


    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(0, 0);
        List<Integer> aList = new ArrayList<>();

    }

    // Helper methods go here
    private void dfs(int s, int parent) {
        marked[s] = true;
        announce();
        for (Integer neighbor : maze.adj(s)) {
            if (neighbor == parent) {
                continue;
            }
            edgeTo[neighbor] = s;
            distTo[neighbor] = distTo[s] + 1;
            announce();
            if (marked[neighbor]) {
                cycleFound = true;
                return;
            }
            if (!marked[neighbor]) {
                dfs(neighbor, s);
                if (cycleFound) {
                    return;
                }
            }
        }
    }
}

