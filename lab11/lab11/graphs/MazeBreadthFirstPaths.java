package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private Maze maze;
    private Queue<Integer> queue;
    private boolean targetFound = false;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        this.maze = m;
        this.s = m.xyTo1D(sourceX, sourceY);
        this.t = m.xyTo1D(targetX, targetY);
        queue = new ArrayDeque<>();
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        queue.add(s);
        marked[s] = true;
        distTo[s] = 0;
        edgeTo[s] = s;
        announce();

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (Integer neighbor : maze.adj(curr)) {
                if (!marked[neighbor]) {
                    marked[neighbor] = true;
                    edgeTo[neighbor] = curr;
                    distTo[neighbor] = distTo[curr] + 1;
                    announce();
                    if (neighbor == t) {
                        return;
                    }
                    queue.add(neighbor);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

