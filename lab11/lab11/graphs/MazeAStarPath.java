package lab11.graphs;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }
    private Queue<Vertex> vertices;
    private void relax(int start, int end) {

        if (distTo[end] > distTo[start] + 1) {
            distTo[end] = distTo[start] + 1;
            edgeTo[end] = start;
            announce();
            
        }
    }
    private class Vertex implements Comparable<Vertex> {
        private int number;
        private int distance;
        public Vertex(int number, int distance) {
            this.number = number;
            this.distance = distance;
        }

        @Override
        public int compareTo(Vertex o) {
            return Integer.compare(this.distance, o.distance);
        }
        public int getNumber() {
            return this.number;
        }
    }
    private void dijkstra(int s) {
        vertices = new PriorityQueue<>();
        vertices.add(new Vertex(s, 0));
        for (int i = 0; i < maze.V() - 1; i++) {
            if (i != s) {
                distTo[i] = Integer.MAX_VALUE;
            }
        }
        marked[s] = true;
        edgeTo[s] = s;
        distTo[s] = 0;
        announce();
        while (!vertices.isEmpty()) {
            Vertex vertex = vertices.poll();
            marked[vertex.getNumber()] = true; // label it as visited
            announce();
            for (Integer neighbor : maze.adj(vertex.getNumber())) {
                if (!marked[neighbor]) {
                    relax(vertex.getNumber(), neighbor);
                    vertices.add(new Vertex(neighbor, distTo[neighbor] + h(neighbor)));
                }
                if (targetFound) {
                    return;
                }
                if (marked[t]) {
                    targetFound = true;
                    return;
                }
            }
        }

    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        dijkstra(s);
    }

    @Override
    public void solve() {
        astar(s);
    }

}

