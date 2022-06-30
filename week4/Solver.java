import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private final Stack<Board> solution = new Stack<Board>();
    private int n;

    public Solver(Board initial) {
        n = 0;
        solve(initial);
    }

    private class Node {

        private final Board board;
        private final int manhattan;
        private final int moves;
        private final Node previous;

        public Node(Board board, int moves, Node previous) {
            this.board = board;
            this.manhattan = board.manhattan();
            this.moves = moves;
            this.previous = previous;
        }
    }

    private static class ManhattanComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            int n1score = n1.moves + n1.manhattan;
            int n2score = n2.moves + n2.manhattan;
            if (n1score == n2score) {
                return n1.manhattan - n2.manhattan;
            }
            else return n1score - n2score;
        }
    }

    private void solve(Board b) {
        MinPQ<Node> pq = new MinPQ<Node>(new ManhattanComparator());
        MinPQ<Node> pqtwin = new MinPQ<Node>(new ManhattanComparator());

        // Step 0
        pq.insert(new Node(b, 0, null));
        pqtwin.insert(new Node(b.twin(), 0, null));

        // Loop.
        Node curr;
        Node currtwin;
        do {
            // Main
            curr = pq.delMin();
            for (Board s : curr.board.neighbors()) {
                // Check for overlap with grandfather
                if (curr.previous == null || !s.equals(curr.previous.board)) {
                    pq.insert(new Node(s, curr.moves + 1, curr));
                }
            }

            // Twin
            currtwin = pqtwin.delMin();
            for (Board s : currtwin.board.neighbors()) {
                if (currtwin.previous == null || !s.equals(currtwin.previous.board)) {
                    pqtwin.insert(new Node(s, currtwin.moves + 1, currtwin));
                }
            }

        } while (!curr.board.isGoal() && !currtwin.board.isGoal());

        // Check if initial board was solved or twin board
        if (curr.board.isGoal()) {
            Node i = curr;
            do {
                solution.push(i.board);
                if (i.previous == null) break;
                else i = i.previous;
            } while (true);
        }
        else {
            n = -1;
        }
    }

    public boolean isSolvable() {
        return n != -1;
    }

    public int moves() {
        return solution.size();
    }

    public Iterable<Board> solution() {
        if (n == -1) return null;
        else return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }


    }
}

