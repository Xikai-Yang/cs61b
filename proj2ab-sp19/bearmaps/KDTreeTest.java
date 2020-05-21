package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    @Test
    public void simpleTest() {
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(2, 3));
        pointList.add(new Point(4, 2));
        pointList.add(new Point(4, 5));
        pointList.add(new Point(3, 3));
        pointList.add(new Point(1, 5));
        pointList.add(new Point(4, 4));
        NaivePointSet naivePointSet = new NaivePointSet(pointList);
        KDTree kdTree = new KDTree(pointList);
        assertEquals(new Point(1, 5), naivePointSet.nearest(0, 7));
        assertEquals(new Point(1, 5), kdTree.nearest(0, 7));
    }


    @Test
    public void RandomTest() {
        Random random = new Random(12345);
        int sampleNum = random.nextInt(100000);
        int bound = 100000;
        List<Point> pointList = new ArrayList<>();
        double randomX = random.nextDouble() * bound;
        double randomY = random.nextDouble() * bound;
        Point randomPoint = new Point(randomX, randomY);
        for (int i = 0; i < sampleNum; i++) {
            while (pointList.contains(randomPoint)) {
                randomX = random.nextDouble() * bound;
                randomY = random.nextDouble() * bound;
                randomPoint = new Point(randomX, randomY);
            }
            pointList.add(randomPoint);
        }

        KDTree kdTree = new KDTree(pointList);
        NaivePointSet naivePointSet = new NaivePointSet(pointList);

        int queryNum = random.nextInt(1000);
        int queryIndex = random.nextInt(sampleNum);
        for (int i = 0; i < queryNum; i++) {
            Point toBeQueried = pointList.get(queryIndex);
            Point naivePoint = naivePointSet.nearest(toBeQueried.getX(), toBeQueried.getY());
            Point kdPoint = kdTree.nearest(toBeQueried.getX(), toBeQueried.getY());
            assertEquals(naivePoint, kdPoint);
        }
    }


}
