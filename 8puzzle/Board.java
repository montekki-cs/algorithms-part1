import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

public class Board {
    private int n;
    private char[][] tiles;
    private int hmng;
    private int manht;
    private int zeroPos1, zeroPos2;

    public Board(int[][] blocks) {
        tiles = new char[blocks.length][blocks.length];
        n = blocks.length;
        hmng = 0;
        manht = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = (char) blocks[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int supposedLabel = i * n + j + 1;

                if (tiles[i][j] == 0) {
                    zeroPos1 = i;
                    zeroPos2 = j;
                    continue;
                }
                else {
                    if (tiles[i][j] != supposedLabel) {

                        int a1 = (tiles[i][j] - 1)/tiles.length;
                        int a2;

                        if (tiles[i][j] % tiles.length == 0) {
                            a2 = tiles.length - 1;
                        }
                        else {
                            a2 = tiles[i][j] % tiles.length - 1;
                        }

                        manht += Math.abs(i - a1) + Math.abs(j - a2);
                        hmng++;
                    }
                }
            }
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return hmng;
    }

    public int manhattan() {
        return manht;
    }

    public boolean isGoal() {
        return hmng == 0;
    }

    public Board twin() {
        int swapPos11, swapPos12;
        int swapPos21, swapPos22;
        int tmp;
        int[][] tmpTiles = new int[tiles.length][tiles.length];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmpTiles[i][j] = tiles[i][j];
            }
        }

        swapPos11 = 0;
        swapPos12 = 0;
        swapPos21 = n - 1;
        swapPos22 = n - 1;
 
        if (tmpTiles[0][0] == 0) {
            swapPos11 = 1;
        }

        if (tmpTiles[n - 1][n - 1] == 0) {
            swapPos22 = n - 2;
        }

        tmp = tmpTiles[swapPos11][swapPos12];
        tmpTiles[swapPos11][swapPos12] = tmpTiles[swapPos21][swapPos22];
        tmpTiles[swapPos21][swapPos22] = tmp;

        return new Board(tmpTiles);
    }

    public boolean equals(Object y) {
        if (y == this) return true;

        if (y == null) return false;

        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        if (that.dimension() != dimension()) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.tiles[i][j] != tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        int[][] tmpTiles = new int[n][n];
        Board b;
        Queue<Board> q = new Queue<Board>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmpTiles[i][j] = tiles[i][j];
            }
        }

        if (zeroPos1 != 0) {
            tmpTiles[zeroPos1][zeroPos2] = tmpTiles[zeroPos1 - 1][zeroPos2];
            tmpTiles[zeroPos1 - 1][zeroPos2] = 0;

            b = new Board(tmpTiles);
            q.enqueue(b);

            tmpTiles[zeroPos1 - 1][zeroPos2] = tmpTiles[zeroPos1][zeroPos2];
            tmpTiles[zeroPos1][zeroPos2] = 0;
        }

        if (zeroPos1 != n - 1) {
            tmpTiles[zeroPos1][zeroPos2] = tmpTiles[zeroPos1 + 1][zeroPos2];
            tmpTiles[zeroPos1 + 1][zeroPos2] = 0;

            b = new Board(tmpTiles);
            q.enqueue(b);

            tmpTiles[zeroPos1 + 1][zeroPos2] = tmpTiles[zeroPos1][zeroPos2];
            tmpTiles[zeroPos1][zeroPos2] = 0;
        }

        if (zeroPos2 != 0) {
            tmpTiles[zeroPos1][zeroPos2] = tmpTiles[zeroPos1][zeroPos2 - 1];
            tmpTiles[zeroPos1][zeroPos2 - 1] = 0;

            b = new Board(tmpTiles);
            q.enqueue(b);

            tmpTiles[zeroPos1][zeroPos2 - 1] = tmpTiles[zeroPos1][zeroPos2];
            tmpTiles[zeroPos1][zeroPos2] = 0;

        }

        if (zeroPos2 != n - 1) {
            tmpTiles[zeroPos1][zeroPos2] = tmpTiles[zeroPos1][zeroPos2 + 1];
            tmpTiles[zeroPos1][zeroPos2 + 1] = 0;

            b = new Board(tmpTiles);

            q.enqueue(b);

            tmpTiles[zeroPos1][zeroPos2 + 1] = tmpTiles[zeroPos1][zeroPos2];
            tmpTiles[zeroPos1][zeroPos2] = 0;
        }

        return q;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(n + "\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", (int) tiles[i][j]));
            }
            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int[][] a = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = StdIn.readInt();
            }
        }

        Board b = new Board(a);

        StdOut.println(b.toString());
        StdOut.println("hamming: " + b.hamming());
        StdOut.println("manhattan: " + b.manhattan());

        for (Board s : b.neighbors()) {
            StdOut.println(s.toString());
            StdOut.println("hamming: " + s.hamming());
            StdOut.println("manhattan: " + s.manhattan());
        }

        StdOut.println("Twin: " + b.twin().toString());
    }
}
