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

public class BruteCollinearPoints {

    private int n = 0;
    private final Bag<LineSegment> lineS = new Bag<LineSegment>();

    // Constructor
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null h bhai");
        }
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

        Point[] tempArray = points.clone();
        Arrays.sort(tempArray);
        analyzePoints(tempArray);
    }

    private void analyzePoints(Point[] points) {

        int length = points.length;

        // P loop
        for (int i = 0; i < length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < length; j++) {
                Point q = points[j];
                // R loop
                for (int k = j + 1; k < length; k++) {
                    Point r = points[k];
                    // Check if 3 are collinear or not
                    if (p.slopeTo(q) == p.slopeTo(r)) {
                        // S loop
                        for (int m = k + 1; m < length; m++) {
                            Point s = points[m];
                            // Check if S is collinear with P
                            if (p.slopeTo(s) == p.slopeTo(q)) {
                                LineSegment temp = new LineSegment(p, s);
                                lineS.add(temp);
                                n++;
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return n;
    }

    public LineSegment[] segments() {
        int size = lineS.size();
        LineSegment[] arr = new LineSegment[size];
        int i = 0;
        for (LineSegment s : lineS) {
            arr[i] = s;
            i++;
        }
        return arr;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
