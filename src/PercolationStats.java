public class PercolationStats {
    private double[] thresholdEstimates;
    private int numTests;

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
        PercolationStats pstats = new PercolationStats(200, 100);
        StdOut.println("mean:\t" + pstats.mean());
    }
}