import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*********************************************************************************************
 * Name: Michael-Bryant Choa
 * Date: July 27, 2015
 * Purpose:  Provides object to perform statistical analysis on Percolation systems to
 *           model and calculate p* threshold estimates such that p > *p will always yield a
 *           percolating system
 * How to use:
 *  1. Use command line and enter "java PercolationStats N T"
 *********************************************************************************************/
public class PercolationStats {
    private double[] thresholdEstimates;    // array to hold on to calculated threshold estimates per test
    private int numTests;                   // number of tests to run

    /**
     * Initializes T percolation objects P of size N and calculates and
     * stores the calculated threshold estimates for each percolation system
     * @param N the percolation grid size
     * @param T the number of test cases
     * @throws java.lang.IllegalArgumentException if N <= 0 or if T <= 0
     */
    public PercolationStats(int N, int T) {
        if(N <= 0)  throw new IllegalArgumentException();
        if(T <= 0)  throw new IllegalArgumentException();

        numTests = T;
        thresholdEstimates = new double[T];
        for(int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            int numOpenSites = 0;
            while(!p.percolates()) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N+1);
                if(!p.isOpen(row, col)) {
                    p.open(row, col);
                    numOpenSites++;
                }
            }
            thresholdEstimates[i] = (double)numOpenSites / (double)(N*N);
        }
    }

    /**
     * Returns the average threshold estimate when system percolates
     * @return mean value of calculated percolation threshold estimates
     */
    public double mean() {
        return StdStats.mean(thresholdEstimates);
    }

    /**
     * Returns the standard deviation of threshold estimates
     * @return standard deviation of calculated percolation threshold estimates
     */
    public double stddev() {
        return StdStats.stddev(thresholdEstimates);
    }

    /**
     * Returns the 95% low confidence interval of threshold estimates
     * @return 95% low confidence interval of threshold estimates
     */
    public double confidenceLo() {
        return mean() - (1.96*stddev())/Math.sqrt(numTests);
    }

    /**
     * Returns the 95% high confidence interval of threshold estimates
     * @return 95% high confidence interval of threshold estimates
     */
    public double confidenceHi() {
        return mean() + (1.96*stddev())/Math.sqrt(numTests);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats pstats = new PercolationStats(N, T);
        StdOut.println("mean:\t\t\t\t\t\t" + pstats.mean());
        StdOut.println("stddev:\t\t\t\t\t\t" + pstats.stddev());
        StdOut.println("95% confidence interval:\t" + pstats.confidenceLo() + ", " + pstats.confidenceHi());
    }
}