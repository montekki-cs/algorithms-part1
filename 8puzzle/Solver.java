import java.util.Comparator;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private static Comparator<SearchNode> boardComparatorManhattan
        = new Comparator<SearchNode>() {
            public int compare(SearchNode node1, SearchNode node2) {
                int priority1 = node1.board.manhattan() + node1.movesMade;
                int priority2 = node2.board.manhattan() + node2.movesMade;

                if (priority1 < priority2) {
                    return -1;
                }
                else if (priority1 == priority2) {
                    if (node1.board.hamming() > node2.board.hamming()) {
                        return 1;
                    }
                    else if (node1.board.hamming() < node2.board.hamming()) {
                        return -1;
                    }
                    return 0;
                }
                return 1;
            }
        };

    private boolean solvable;
    private int numMoves;
    private MinPQ<SearchNode> mpq;
    private Stack<Board> solution;

    private class SearchNode {
        private int movesMade;
        private Board board;
        private SearchNode next;
    }

    public Solver(Board initial) {
        int turn = 0;
        SearchNode prevSearchNode = null;
        SearchNode prevSearchNodeTwin = null;
        SearchNode searchNode;
        SearchNode searchNodeTwin;
        solvable = false;
        MinPQ<SearchNode> mpqTwin;

        if (initial == null) {
            throw new java.lang.NullPointerException("null passed to constructor");
        }

        Board twinInitial = initial.twin();

        SearchNode node = new SearchNode();
        node.board = initial;
        node.movesMade = 0;
        node.next = null;

        SearchNode nodeTwin = new SearchNode();
        nodeTwin.board = twinInitial;
        nodeTwin.movesMade = 0;
        nodeTwin.next = null;

        mpq = new MinPQ<SearchNode>(boardComparatorManhattan);
        mpqTwin = new MinPQ<SearchNode>(boardComparatorManhattan);

        mpq.insert(node);
        mpqTwin.insert(nodeTwin);

        do {
            if (turn == 0) {
                turn = 1;
                searchNode = mpq.delMin();

                if (searchNode.board.isGoal()) {
                    solvable = true;
                    break;
                }

                for (Board neighbor : searchNode.board.neighbors()) {
                    if (searchNode.next != null) {
                        if (searchNode.next.board.equals(neighbor)) {
                            continue;
                        }
                    }

                    SearchNode neighborNode = new SearchNode();
                    neighborNode.board = neighbor;
                    neighborNode.next = searchNode;
                    neighborNode.movesMade = searchNode.movesMade + 1;

                    mpq.insert(neighborNode);
                }

                prevSearchNode = searchNode;
            }
            else {
                turn = 0;
                searchNodeTwin = mpqTwin.delMin();

                if (searchNodeTwin.board.isGoal()) {
                    searchNode = searchNodeTwin;
                    break;
                }

                for (Board neighbor : searchNodeTwin.board.neighbors()) {
                    if (searchNodeTwin.next != null) {
                        if (searchNodeTwin.next.board.equals(neighbor)) {
                            continue;
                        }
                    }

                    SearchNode neighborNode = new SearchNode();
                    neighborNode.board = neighbor;
                    neighborNode.next = searchNodeTwin;
                    neighborNode.movesMade = searchNodeTwin.movesMade + 1;

                    mpqTwin.insert(neighborNode);
                }

                prevSearchNodeTwin = searchNodeTwin;
            }
        } while (true);

        numMoves = 0;
        solution = new Stack<Board>();

        while (searchNode != null) {
            solution.push(searchNode.board);
            searchNode = searchNode.next;
            numMoves++;
        }

        if (numMoves > 0) {
            numMoves--;
        }

        while (!mpq.isEmpty()) {
            mpq.delMin();
        }
        while (!mpqTwin.isEmpty()) {
            mpqTwin.delMin();
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (isSolvable()) {
            return numMoves;
        }
        else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Queue<Board> q = new Queue<Board>();

        for (Board s : solution) {
            q.enqueue(s);
        }

        return q;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
