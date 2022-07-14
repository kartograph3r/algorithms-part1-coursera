import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {

    // Global variables
    private Node root;
    private int n;
    private Point2D champion;

    // The node abstraction for KdTree
    private class Node {
        private final Point2D point;
        private final RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D point, RectHV rect, Node left, Node right) {
            this.point = point;
            this.rect = rect;
            this.left = left;
            this.right = right;
        }
    }

    // Constructor
    public KdTree() {
        root = null;
        n = 0;
    }

    // Is our point set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // Number of points
    public int size() {
        return n;
    }

    // Public insert function
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument!");
        if (insertHelper(p)) {
            n++;
        }
    }

    // Private inserter head
    private boolean insertHelper(Point2D p) {
        // If you're doing the insert for the first time
        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1), null, null);
            return true;
        }
        // Insert for tree with root
        Node x = root;
        // Will keep track of x or y insert
        int dim = 0;
        while (true) {
            // If duplicate point
            if (x.point.equals(p)) return false;
            // X insert
            if (dim == 0) {
                dim = 1;
                // Go Left
                if (Double.compare(p.x(), x.point.x()) < 0) {
                    // If null, insert node
                    if (x.left == null) {
                        // New node on the left half
                        x.left = new Node(p, new RectHV(x.rect.xmin(), x.rect.ymin(), x.point.x(),
                                                        x.rect.ymax()), null, null);
                        return true;
                    }
                    // Search on left tree
                    else {
                        x = x.left;
                        continue;
                    }
                }
                // Go right
                // If null, insert node
                if (x.right == null) {
                    // New node on the right half
                    x.right = new Node(p, new RectHV(x.point.x(), x.rect.ymin(), x.rect.xmax(),
                                                     x.rect.ymax()), null, null);
                    return true;
                }
                // Search on right tree
                x = x.right;
            }
            // Y insert
            else {
                dim = 0;
                // Go left
                if (Double.compare(p.y(), x.point.y()) < 0) {
                    // If null insert
                    if (x.left == null) {
                        // New node on the bottom half
                        x.left = new Node(p, new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(),
                                                        x.point.y()), null, null);
                        return true;
                    }
                    // Else search on left tree
                    else {
                        x = x.left;
                        continue;
                    }
                }
                // Go right
                // If null, insert node
                if (x.right == null) {
                    // New node on the upper half
                    x.right = new Node(p, new RectHV(x.rect.xmin(), x.point.y(), x.rect.xmax(),
                                                     x.rect.ymax()), null, null);
                    return true;
                }
                // Search on right tree
                x = x.right;
            }
        }
    }

    // Public contains(point)? function
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument!");
        return containsHelper(p);
    }

    // Private contains function
    private boolean containsHelper(Point2D p) {
        Node x = root;
        int dim = 0;
        // Our iterator
        while (true) {
            // If node where point is supposed to be is null, return false
            if (x == null) return false;
            // Return true if we found our point
            if (x.point.equals(p)) return true;
            // X contains
            if (dim == 0) {
                dim = 1;
                // If p.x() < x.point.x() go left
                if (Double.compare(p.x(), x.point.x()) < 0) {
                    x = x.left;
                }
                // Else go right
                else x = x.right;
            }
            else {
                dim = 0;
                // If p.y() < x.point.y() go left
                if (Double.compare(p.y(), x.point.y()) < 0) {
                    x = x.left;
                }
                // Else go right
                else x = x.right;
            }
        }
    }

    public void draw() {

    }

    // All points in a rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument!");
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        rangeHelper(root, rect, list);
        return list;
    }

    // Helper range function
    private void rangeHelper(Node x, RectHV rect, ArrayList<Point2D> list) {
        if (x == null) return;
        if (rect.intersects(x.rect)) {
            if (rect.contains(x.point)) list.add(x.point);
            rangeHelper(x.left, rect, list);
            rangeHelper(x.right, rect, list);
        }
    }

    // Nearest neighbour in the set to point p
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument!");
        if (root == null) return null;
        champion = root.point;
        nearestHelper(root, p, 0);
        return champion;
    }

    // Private nearest helper
    private void nearestHelper(Node x, Point2D p, int dim) {
        // Return hit a null node
        if (x == null) return;
        // Prune if dead end rectangle
        if (x.rect.distanceSquaredTo(p) > p.distanceSquaredTo(champion)) return;
        // Check for new champion
        if (Double.compare(p.distanceSquaredTo(x.point), p.distanceSquaredTo(champion)) < 0) {
            // Update champion
            champion = x.point;
        }
        // Actual recursion down both children
        // If x coordinate based point
        if (dim == 0) {
            // If point is to the left of current point
            if (Double.compare(p.x(), x.point.x()) < 0) {
                nearestHelper(x.left, p, 1);
                nearestHelper(x.right, p, 1);
            }
            else {
                nearestHelper(x.right, p, 1);
                nearestHelper(x.left, p, 1);
            }
        }
        // Y coordinate based point
        else {
            // Point is below the current point
            if (Double.compare(p.y(), x.point.y()) < 0) {
                nearestHelper(x.left, p, 0);
                nearestHelper(x.right, p, 0);
            }
            else {

                nearestHelper(x.right, p, 0);
                nearestHelper(x.left, p, 0);
            }
        }
    }

    public static void main(String[] args) {
        // Empty on purpose
    }


}
