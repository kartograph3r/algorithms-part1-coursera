/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONSTANT96 = 1.96;
    private final double[] threshold; // stores the individual threshold for each iteration
    private final double trial;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be positive integers!");
        }
        threshold = new double[trials];
        trial = trials;
        for (int k = 0; k < trials; k++) {
            Percolation currentTrial = new Percolation(n);
            while (!currentTrial.percolates()) {
                currentTrial.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            threshold[k] = currentTrial.numberOfOpenSites() / (double) (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }


    public double confidenceLo() {
        return mean() - (CONSTANT96 * stddev()) / Math.sqrt(trial);
    }

    public double confidenceHi() {
        return mean() + (CONSTANT96 * stddev()) / Math.sqrt(trial);
    }

    public static void main(String[] args) {
        PercolationStats plsWork = new PercolationStats(Integer.parseInt(args[0]),
                                                        Integer.parseInt(args[1]));
        StdOut.println("mean = " + plsWork.mean());
        StdOut.println("stddev = " + plsWork.stddev());
        StdOut.println("95% confidence interval = " + "[" + plsWork.confidenceLo() + ", " + plsWork
                .confidenceHi() + "]");
    }
}
