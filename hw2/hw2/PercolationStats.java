package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] ratioArray;
    private int times;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.ratioArray = new double[T];
        this.times = T;
        for (int i = 0; i < T; i++) {
            this.ratioArray[i] = simulation(N, pf);
        }
    }

    private double simulation(int N, PercolationFactory pf) {
        Percolation percolation = pf.make(N);
        while (!percolation.percolates()) {
            int locationX = StdRandom.uniform(0, N);
            int locationY = StdRandom.uniform(0, N);
            percolation.open(locationX, locationY);
        }
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(N * N);
        double ratio = (double) percolation.numberOfOpenSites() / (double) (N * N);
        System.out.println(ratio);
        return ratio;
    }
    public double mean() {
        return StdStats.mean(this.ratioArray);
    }
    public double stddev() {
        return StdStats.stddev(this.ratioArray);
    }
    public double confidenceLow() {
        return mean() - (stddev() * 1.96) / Math.sqrt(times);
    }
    public double confidenceHigh() {
        return mean() + (stddev() * 1.96) / Math.sqrt(times);
    }
    
}
