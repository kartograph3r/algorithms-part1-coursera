/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    // Number of line segments
    private int n = 0;
    private final Bag<LineSegment> lineS = new Bag<LineSegment>();

    public FastCollinearPoints(Point[] points) {

        // Check if argument is null
        if (points == null) {
            throw new IllegalArgumentException("null h bhai");
        }
        // Check if any point is null and check for repeated points
        int tempi = 0;
        for (Point s : points) {
            if (s == null) {
                throw new IllegalArgumentException("null h");
            }
            for (int i = tempi + 1; i < points.length; i++) {
                if (s.equals(points[i])) {
                    throw new IllegalArgumentException("scam karta");
                }
            }
            tempi++;
        }

        int length = points.length;

        // Create an auxiliary array to prevent mutation of the original array.
        Point[] auxArray = points.clone();

        // Sort the aux array according the slope w.r.t to each point in the original array
        for (int i = 0; i < length; i++) {
            // Sort aux array
            Point p = points[i];
            Arrays.sort(auxArray, p.slopeOrder());
            int counter = 1;
            for (int j = 1; j < length; j++) {
                if (p.slopeTo(auxArray[j - 1]) != p.slopeTo(auxArray[j])) {
                    if (counter >= 3) {
                        newLineSeg(auxArray, j, counter);
                        counter = 0;
                    }
                    else counter = 0;
                }
                // End point case
                if (j == length - 1 && counter >= 3) {
                    newLineSeg(auxArray, j, counter);
                }
                else counter++;
            }
        }

    }

    private void newLineSeg(Point[] tempArray, int j, int counter) {
        // Check if points are in natural order
        Point[] legal = new Point[counter + 1];
        legal[0] = tempArray[0];
        for (int i = 1; i <= counter; i++) {
            legal[i] = tempArray[j - counter + i - 1];
        }
        Arrays.sort(legal);
        if (tempArray[0] != legal[0]) return;
        LineSegment bingo = new LineSegment(legal[0], legal[counter]);
        lineS.add(bingo);
        n++;
    }

    public int numberOfSegments() {
        return n;
    }

    public LineSegment[] segments() {
        LineSegment[] voila = new LineSegment[lineS.size()];
        int i = 0;
        for (LineSegment s : lineS) {
            voila[i] = s;
            i++;
        }
        return voila;
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
