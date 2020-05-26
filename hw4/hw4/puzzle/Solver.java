package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;


import java.util.*;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private WorldState worldState;
        private int moves;
        private SearchNode prev;
        private int estimatedDistance = -1;


        public SearchNode(WorldState worldState, int moves, SearchNode prev) {
            this.worldState = worldState;
            this.moves = moves;
            this.prev = prev;
            this.estimatedDistance = worldState.estimatedDistanceToGoal();
        }
        public SearchNode(WorldState worldState) {
            this(worldState, 0, null);
        }
        public WorldState getWorldState() {
            return this.worldState;
        }
        public int getMoves() {
            return this.moves;
        }

        @Override
        public int hashCode() {
            return this.worldState.hashCode();
        }
        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!o.getClass().equals(this.getClass())) {
                return false;
            }
            if (o == this) {
                return true;
            }
            return this.hashCode() == o.hashCode();
        }

        private int roughDistance() {
            return this.estimatedDistance + this.moves;
        }
        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.roughDistance(), o.roughDistance());
        }
    }
    private SearchNode initial;
    private MinPQ<SearchNode> minPQ;
    private Set<SearchNode> searchNodeSet;
    private Stack<WorldState> stateStack;
    private int enqueueTimes = 0;
    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     * @param initial
     */
    public Solver(WorldState initial) {
        this.initial = new SearchNode(initial);
        this.minPQ = new MinPQ<>();
        this.searchNodeSet = new HashSet<>();
        this.minPQ.insert(this.initial);

        enqueueTimes++;
        this.stateStack = helperSolver();
    }

    private Stack<WorldState> helperSolver() {
        Stack<WorldState> worldStateStack = new Stack<>();
        if (this.initial.getWorldState().isGoal()) {
            worldStateStack.push(this.initial.getWorldState());
            return worldStateStack;
        }
        while (!minPQ.isEmpty()) {
            SearchNode searchNode = minPQ.delMin();

            this.searchNodeSet.add(searchNode);
            for (WorldState neighbor : searchNode.getWorldState().neighbors()) {
                boolean condition = searchNode.prev == null || !neighbor.equals(searchNode.prev.getWorldState());

                if (condition) {
                    minPQ.insert(new SearchNode(neighbor, searchNode.getMoves() + 1, searchNode));
                    enqueueTimes++;
                }
                /*
                boolean condition2 = searchNodeSet.contains(new SearchNode(neighbor, 0, null));
                if (!condition2) {
                    SearchNode node = new SearchNode(neighbor, searchNode.getMoves() + 1, searchNode);
                    System.out.println(neighbor +" " + node.roughDistance());
                    //System.out.println(neighbor+" " + (searchNode.getMoves() + 1 + neighbor.estimatedDistanceToGoal()));
                    minPQ.insert(new SearchNode(neighbor, searchNode.getMoves() + 1, searchNode));
                    enqueueTimes++;
                }
               */


                if (neighbor.isGoal()) {
                    worldStateStack.push(neighbor);
                    SearchNode temp = searchNode;
                    while (temp != null) {
                        worldStateStack.push(temp.getWorldState());
                        temp = temp.prev;
                    }
                    return worldStateStack;
                }
            }
        }
        return null;
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     * @return
     */
    public int moves() {
        return stateStack.size() - 1;
    }

    public int getEnqueueTimes() {
        return this.enqueueTimes;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     * @return
     */
    public Iterable<WorldState> solution() {
        return this.stateStack;
    }
}
