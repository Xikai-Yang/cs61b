package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private AStarGraph<Vertex> graph;
    private Vertex start;
    private Vertex goal;
    private ExtrinsicMinPQ<Vertex> minPQ;
    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> edgeTo;
    private SolverOutcome solverOutcome;
    private List<Vertex> solution;
    private int numStatesExplored;
    private double explorationTime;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.minPQ = new ArrayHeapMinPQ<>();
        this.graph = input;
        this.start = start;
        this.goal = end;
        this.distTo = new HashMap<>();
        this.edgeTo = new HashMap<>();
        this.solution = null;
        this.numStatesExplored = 0;
        this.explorationTime = 0;

        minPQ.add(start, graph.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        helper(timeout);
    }
    private void helper(double timeout) {
        Stopwatch sw = new Stopwatch();
        while (minPQ.size() > 0) {
            Vertex p = minPQ.removeSmallest();
            numStatesExplored++;
            if (p.equals(goal)) {
                solverOutcome = SolverOutcome.SOLVED;
                this.explorationTime = sw.elapsedTime();
                setSolution();
                return;
            }
            for (WeightedEdge<Vertex> edge : graph.neighbors(p)) {
                relax(edge);
            }
            if (sw.elapsedTime() > timeout) {
                this.explorationTime = sw.elapsedTime();
                solverOutcome = SolverOutcome.TIMEOUT;
                return;
            }
        }
        this.explorationTime = sw.elapsedTime();
        solverOutcome = SolverOutcome.UNSOLVABLE;
    }

    private double priority(Vertex vertex) {
        if (!distTo.containsKey(vertex)) {
            throw new IllegalArgumentException();
        }
        return graph.estimatedDistanceToGoal(vertex, goal) + distTo.get(vertex);
    }

    private void relax(WeightedEdge<Vertex> edge) {
        // TODO
        Vertex p = edge.from();
        Vertex q = edge.to();
        double weight = edge.weight();
        if (distTo.containsKey(q)) {
            assert edgeTo.containsKey(q);
            double newDistance = distTo.get(p) + weight;
            if (newDistance < distTo.get(q)) {
                // update relevant information
                distTo.put(q, newDistance);
                edgeTo.put(q, p);
                minPQ.changePriority(q, priority(q));
            }
        } else {
            assert !minPQ.contains(q);
            double newDistance = distTo.get(p) + weight;
            distTo.put(q, newDistance);
            edgeTo.put(q, p);
            minPQ.add(q, priority(q));
        }
    }

    @Override
    public SolverOutcome outcome() {
        return solverOutcome;
    }

    private void setSolution() {

        List<Vertex> vertexList = new ArrayList<>();
        System.out.println(solverOutcome);

        if (solverOutcome.equals(SolverOutcome.UNSOLVABLE) || solverOutcome.equals(SolverOutcome.TIMEOUT)) {
            this.solution = vertexList;
            return;
        }

        Vertex vertex = goal;
        while (vertex != null) {
            vertexList.add(vertex);
            vertex = edgeTo.get(vertex);
        }
        List<Vertex> sortedVertexList = new ArrayList<>();
        for (int i = vertexList.size() - 1; i >= 0; --i) {
            Vertex item = vertexList.get(i);
            sortedVertexList.add(item);
        }
        this.solution = sortedVertexList;
    }
    @Override
    public List<Vertex> solution() {
        return this.solution;
    }



    @Override
    public double solutionWeight() {
        if (solverOutcome.equals(SolverOutcome.UNSOLVABLE) || solverOutcome.equals(SolverOutcome.TIMEOUT)) {
            return 0;
        }
        return distTo.get(goal);
    }

    @Override
    public int numStatesExplored() {
        return this.numStatesExplored - 1;
    }

    @Override
    public double explorationTime() {
        return this.explorationTime;
    }


}
