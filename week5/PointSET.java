import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {

    // BST to store points
    private final SET<Point2D> points;

    // Constructor
    public PointSET() {
        points = new SET<Point2D>();
    }

    // Is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // Size
    public int size() {
        return points.size();
    }

    // Check if point is not already in set and then insert
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        if (!contains(p)) {
            points.add(p);
        }
    }

    // Check if set contains point p
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        return points.contains(p);
    }

    public void draw() {

    }

    // All points in a given rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("null argument");
        ArrayList<Point2D> temp = new ArrayList<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                temp.add(p);
            }
        }
        return temp;
    }

    // Nearest point to point P
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        Point2D minDistance = null;
        for (Point2D s : points) {
            if (minDistance == null
                    || Double.compare(s.distanceSquaredTo(p), minDistance.distanceSquaredTo(p))
                    < 0) {
                minDistance = s;
            }
        }
        return minDistance;
    }

    public static void main(String[] args) {
        // Empty on purpose
    }
}
