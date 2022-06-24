/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> us = new RandomizedQueue<String>();
        int n = Integer.parseInt(args[0]);
        String s;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            us.enqueue(s);
        }
        for (int i = 0; i < n; i++) {
            StdOut.println(us.dequeue());
        }
    }
}
