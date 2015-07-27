public class PercolationStats {
    private double[] thresholdEstimates;
    private int numTests;

    /**
     * Initializes T percolation objects P and calculates the threshold estimate
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
                int row = StdRandom.uniform(1, N);
                int col = StdRandom.uniform(1, N);
                if(!p.isOpen(row, col)) {
                    p.open(row, col);
                    numOpenSites++;
                }
            }
            thresholdEstimates[i] = numOpenSites / (N*N);
        }
    }

    public double mean() {
        return StdStats.mean(thresholdEstimates);
    }

    public double stddev() {
        return StdStats.stddev(thresholdEstimates);
    }

    public double confidenceLo() {
        return mean() - (1.96*stddev())/Math.sqrt(numTests);
    }

    public double confidenceHi() {
        return mean() + (1.96*stddev())/Math.sqrt(numTests);
    }

    public static void main(String[] args) {
        Stopwatch watch = new Stopwatch();
        PercolationStats pstats = new PercolationStats(20, 100);
        double runtime = watch.elapsedTime();
        StdOut.println("total runtime:\t" + runtime);
        StdOut.println("mean:\t\t\t\t\t\t" + pstats.mean());
        StdOut.println("stddev:\t\t\t\t\t\t" + pstats.stddev());
        StdOut.println("95% confidence interval:\t" + pstats.confidenceLo() + ", " + pstats.confidenceHi());

    }
}