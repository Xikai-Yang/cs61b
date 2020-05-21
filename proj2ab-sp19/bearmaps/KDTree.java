package bearmaps;

import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet {
    private class Node {
        private Point point;
        private PointComparator pointComparator;
        public Node left;
        public Node right;
        public Node(Point point, boolean axis, Node left, Node right) {
            this.point = point;
            this.left = left;
            this.right = right;
            this.pointComparator = new PointComparator(axis);
        }
        public Node(Point point, boolean axis) {
            this(point, axis, null, null);
        }
        public Point getPoint() {
            return this.point;
        }
        public PointComparator getPointComparator() {
            return this.pointComparator;
        }
    }

    private class PointComparator implements Comparator<Point> {
        private boolean xAxis;
        public PointComparator(boolean xAxis) {
            this.xAxis = xAxis;
        }
        public PointComparator() {
            this.xAxis = true;
        }
        public boolean getAxis() {
            return this.xAxis;
        }
        @Override
        public int compare(Point o1, Point o2) {
            if (this.xAxis) {
                return Double.compare(o1.getX(), o2.getX());
            }
            return Double.compare(o1.getY(), o2.getY());
        }
    }

    private Node root;
    private boolean contains(Point point) {
        return search(point) != null;
    }
    private Node search(Point point) {
        // TODO
        Node node = searchHelper(point, root);
        return node;
    }

    private Node searchHelper(Point point, Node x) {
        if (x == null) {
            return null;
        }
        int cmp = x.getPointComparator().compare(x.point, point);
        if (cmp < 0) {
            return searchHelper(point, x.right);
        }
        if (cmp > 0) {
            return searchHelper(point, x.left);
        }
        return x;
    }

    private Node putHelper(Point point, Node x, boolean xAxis) {
        if (x == null) {
            return new Node(point, xAxis);
        }
        int cmp = x.getPointComparator().compare(x.point, point);
        if (cmp < 0) {
            x.right = putHelper(point, x.right, !xAxis);
        }
        if (cmp > 0) {
            x.left = putHelper(point, x.left, !xAxis);
        }
        return x;
    }
    private void put(Point point) {
        root = putHelper(point, root, true);
    }


    public KDTree(List<Point> points) {
        // You can assume points has at least size 1.
        for (Point point : points) {
            this.put(point);
        }
    }

    private double roughDistance(Node node, Point goal) {
        boolean xAxis = node.getPointComparator().getAxis();
        Point point = node.getPoint();
        double delta = 0x7fffffff;
        if (xAxis) {
            // use xAxis to check
            delta = point.getX() - goal.getX();
        } else {
            delta = point.getY() - goal.getY();
        }
        return delta * delta;
    }
    private Node nearestHelper(Node curr, Node best, Point goal) {
        if (curr == null) {
            return best;
        }
        double distance = Point.distance(curr.getPoint(), goal);
        double bestDistance = Point.distance(best.getPoint(), goal);
        if (distance < bestDistance) {
            best = curr;
        }
        // decide which one is the best side
        int cmp = curr.getPointComparator().compare(curr.getPoint(), goal);
        Node goodSide = null;
        Node badSide = null;
        if (cmp == 0) {
            return best;
        }
        if (cmp < 0) {
            goodSide = curr.right;
            badSide = curr.left;
        }
        if (cmp > 0) {
            goodSide = curr.left;
            badSide = curr.right;
        }
        // until now we have decided which one is the better side.
        best = nearestHelper(goodSide, best, goal);
        double roughDistance = this.roughDistance(curr, goal);
        if (roughDistance < bestDistance) {
            best = nearestHelper(badSide, best, goal);
        }
        return best;

    }
    @Override
    public Point nearest(double x, double y) {
        // O(log)
        Point goal = new Point(x, y);
        Node nearestNode = nearestHelper(root, root, goal);
        return nearestNode.getPoint();
    }
}
