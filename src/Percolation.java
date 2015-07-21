public class Percolation {
    private class Site {
        int id;
        boolean open;
    }

    private QuickFindUF uf;
    private Site[][] grid;
    private int top;
    private int bottom;
    private int size;

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

    public void open(int i, int j) {
        if (isBoundsOutOfRange(i, j))
            throw new IndexOutOfBoundsException();
        int gridI = i - 1;
        int gridJ = j - 1;
        grid[gridI][gridJ].open = true;
        if (!isBoundsOutOfRange(i - 1, j) && isOpen(i - 1, j))
            uf.union(grid[gridI - 1][gridJ].id, grid[gridI][gridJ].id);
        if (!isBoundsOutOfRange(i, j - 1) && isOpen(i, j - 1))
            uf.union(grid[gridI][gridJ - 1].id, grid[gridI][gridJ].id);
        if (!isBoundsOutOfRange(i + 1, j) && isOpen(i + 1, j))
            uf.union(grid[gridI + 1][gridJ].id, grid[gridI][gridJ].id);
        if (!isBoundsOutOfRange(i, j + 1) && isOpen(i, j + 1))
            uf.union(grid[gridI][gridJ + 1].id, grid[gridI][gridJ].id);
    }

    public boolean isOpen(int i, int j) {
        if (isBoundsOutOfRange(i, j))
            throw new IndexOutOfBoundsException();
        return grid[i - 1][j - 1].open;
    }

    public boolean isFull(int i, int j) {
        if (isBoundsOutOfRange(i, j))
            throw new IndexOutOfBoundsException();
        return grid[i - 1][j - 1].open && uf.connected(grid[i - 1][j - 1].id, top);
    }

    private boolean isBoundsOutOfRange(int i, int j) {
        return i < 1 || i >= size + 1 || j < 1 || j >= size + 1;
    }

    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    /*
    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        StdOut.println(p.isFull(1, 1));
        StdOut.println(p.isFull(1, 2));
        StdOut.println(p.isFull(2, 1));
        StdOut.println(p.isFull(2, 2));
        StdOut.println("System percolates?:\t" + p.percolates());
        p.open(2, 1);
        StdOut.println(p.isFull(1, 1));
        StdOut.println(p.isFull(1, 2));
        StdOut.println(p.isFull(2, 1));
        StdOut.println(p.isFull(2, 2));
        StdOut.println("System percolates?:\t" + p.percolates());
    }
    */
}