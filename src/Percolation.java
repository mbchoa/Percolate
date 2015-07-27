/*********************************************************************************************
 * Name: Michael-Bryant Choa
 * Date: July 26, 2015
 * Purpose:  Provides an object to model a 2D, N-by-N percolation system
 * How to use:
 *  1. Initialize Peroclation with parameter N, specifying the grid dimensions
 *  2. Make calls to the open() method, specifying the position of the site to open
 *  3. Call isOpen(), isFull() and percolates() methods to determine state of the system
 *********************************************************************************************/

public class Percolation {
    /**
     * Internal class representing a percolation site within percolation system
     */
    private class Site {
        int id;                 // id used to identify site connectivity with other sites
        boolean open;           // true if site is open, false if site is blocked
    }

    private QuickFindUF uf;     // union-find data structure to keep track of system connectivity
    private Site[][] grid;      // 2D array data structure to keep to map system
    private int top;            // virtual site connecting the top sites
    private int bottom;         // virtual site connecting the bottom sites
    private int size;           // size of grid

    /**
     * Initializes a 2D array data structure with N*N components and a QuickFindUF object
     * @throws java.lang.IllegalArgumentException if N <= 0
     * @param N the number of objects
     */
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException();
        size = N;
        uf = new QuickFindUF(N * N + 2);
        int id = 0;
        top = id++;
        grid = new Site[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = new Site();
                grid[i][j].id = id++;
                grid[i][j].open = false;
            }
        }
        bottom = id;
        for (int k = 0; k < N; k++) {
            uf.union(grid[0][k].id, top);
            uf.union(grid[N - 1][k].id, bottom);
        }
    }

    /**
     * Opens the percolation site at location (x, y) in the grid
     * @param x the x coordinate of the site position
     * @param y the y coordinate of the site position
     */
    public void open(int x, int y) {
        if (isBoundsOutOfRange(x, y))
            throw new IndexOutOfBoundsException();
        int gridI = x - 1;
        int gridJ = y - 1;
        grid[gridI][gridJ].open = true;
        if (!isBoundsOutOfRange(x - 1, y) && isOpen(x - 1, y))
            uf.union(grid[gridI - 1][gridJ].id, grid[gridI][gridJ].id);
        if (!isBoundsOutOfRange(x, y - 1) && isOpen(x, y - 1))
            uf.union(grid[gridI][gridJ - 1].id, grid[gridI][gridJ].id);
        if (!isBoundsOutOfRange(x + 1, y) && isOpen(x + 1, y))
            uf.union(grid[gridI + 1][gridJ].id, grid[gridI][gridJ].id);
        if (!isBoundsOutOfRange(x, y + 1) && isOpen(x, y + 1))
            uf.union(grid[gridI][gridJ + 1].id, grid[gridI][gridJ].id);
    }

    /**
     * Returns if the percolation site at (x, y) is open
     * @param x the column position of site
     * @param y the row position of the site
     * @return true if the site at the position is open, false otherwise
     */
    public boolean isOpen(int x, int y) {
        if (isBoundsOutOfRange(x, y))
            throw new IndexOutOfBoundsException();
        return grid[x - 1][y - 1].open;
    }

    /**
     * Returns if the percolation site at (x, y) is filled
     * @param x the column position of the site
     * @param y the row position of the site
     * @return true if the site at the position is full, false otherwise
     */
    public boolean isFull(int x, int y) {
        if (isBoundsOutOfRange(x, y))
            throw new IndexOutOfBoundsException();
        return grid[x - 1][y - 1].open && uf.connected(grid[x - 1][y - 1].id, top);
    }

    /**
     * Returns if the site position is within bounds of percolation grid
     * @param x the column position of the site
     * @param y the row position of the site
     * @return true if the position is within bounds of grid, false otherwise
     */
    private boolean isBoundsOutOfRange(int x, int y) {
        return x < 1 || x >= size + 1 || y < 1 || y >= size + 1;
    }

    /**
     * Returns if the system percolates
     * @return true if the system percolates, false otherwise
     */
    public boolean percolates() { return uf.connected(top, bottom);}
}