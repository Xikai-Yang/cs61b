package bearmaps;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> pointList;
    public NaivePointSet(List<Point> points) {
        // You can assume points has at least size 1
        this.pointList = points;
    }
    @Override
    public Point nearest(double x, double y) {
        Point reference = new Point(x, y);
        double min = 0x7fffffff;
        Point nearestPoint = null;
        for (Point point : pointList) {
            double distance = Point.distance(point, reference);
            if (distance < min) {
                nearestPoint = point;
                min = distance;
            }
        }
        return nearestPoint;
    }
    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        assert ret.getX() == 3.3; // evaluates to 3.3
        assert ret.getY() == 4.4; // evaluates to 4.4
    }
}
