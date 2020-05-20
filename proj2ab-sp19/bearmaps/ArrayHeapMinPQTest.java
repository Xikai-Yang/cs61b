package bearmaps;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void testOutputs() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();

        for (int i = 1; i <= 200; i++) {
            minPQ.add(i, i);
        }
        minPQ.changePriority(1, 201);
        assertEquals(2, (int) minPQ.getSmallest());
        assertEquals(2, (int) minPQ.removeSmallest());
    }

    @Test
    public void testAdd() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100; i++) {
            minPQ.add(i, i);
        }
        assertEquals(minPQ.size(), 100);
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100; i++) {
            minPQ.add(i, i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(true,minPQ.contains(i));
        }
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        List<Integer> aList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int randomNum = random.nextInt(100);
            while (aList.contains(randomNum)) {
                randomNum = random.nextInt(100);
            }
            aList.add(randomNum);
            minPQ.add(randomNum, randomNum);
        }
        int min = Collections.min(aList);
        assertEquals(min, (int)minPQ.getSmallest());
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        List<Integer> aList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int randomNum = random.nextInt(100);
            while (aList.contains(randomNum)) {
                randomNum = random.nextInt(100);
            }
            aList.add(randomNum);
            minPQ.add(randomNum, randomNum);
        }
        int min = Collections.min(aList);
        int minIndex = aList.indexOf(min);
        aList.remove(minIndex);
        int nextMin = Collections.min(aList);
        assertEquals(min, (int) minPQ.removeSmallest());
        assertEquals(nextMin, (int) minPQ.getSmallest());
        assertEquals(nextMin, (int) minPQ.removeSmallest());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100; i++) {
            minPQ.add(i, i);
        }
        minPQ.changePriority(99, -100);
        assertEquals(99, (int) minPQ.removeSmallest());
        assertEquals(0, (int) minPQ.getSmallest());
        minPQ.changePriority(0, 2);
        assertEquals(1, (int) minPQ.getSmallest());
        minPQ.removeSmallest();
    }
    private void testAddSpeedHelper(int O) {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveMinPQ = new NaiveMinPQ<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < O; i += 1) {
            minHeap.add(i, O - i);
        }
        long end = System.currentTimeMillis();
        System.out.println("My Heap Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
        long nextStart = System.currentTimeMillis();
        for (int i = 0; i < O; i += 1) {
            naiveMinPQ.add(i, O - i);
        }
        long nextEnd = System.currentTimeMillis();
        System.out.println("Naive Heap Total time elapsed: " + (nextEnd - nextStart) / 1000.0 +  " seconds.");
    }
    private void testContainsSpeedHelper(int O) {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveMinPQ = new NaiveMinPQ<>();
        for (int i = 0; i < O; i += 1) {
            minHeap.add(i, O - i);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < O; i += 1) {
            minHeap.contains(i);
        }
        long end = System.currentTimeMillis();

        System.out.println("My Heap Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");

        for (int i = 0; i < O; i += 1) {
            naiveMinPQ.add(i, O - i);
        }
        long nextStart = System.currentTimeMillis();
        for (int i = 0; i < O; i += 1) {
            naiveMinPQ.contains(i);
        }
        long nextEnd = System.currentTimeMillis();
        System.out.println("Naive Heap Total time elapsed: " + (nextEnd - nextStart) / 1000.0 +  " seconds.");
    }

    private void testRemoveSmallestSpeedHelper(int O) {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveMinPQ = new NaiveMinPQ<>();
        for (int i = 0; i < O; i += 1) {
            minHeap.add(i, O - i);
            naiveMinPQ.add(i, O - i + 1);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < O; i += 1) {
            minHeap.removeSmallest();
        }
        long end = System.currentTimeMillis();
        System.out.println("My Heap Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
        long nextStart = System.currentTimeMillis();
        for (int i = 0; i < O; i += 1) {
            naiveMinPQ.contains(i);
        }
        long nextEnd = System.currentTimeMillis();
        System.out.println("My Heap Total time elapsed: " + (nextEnd - nextStart) / 1000.0 +  " seconds.");
    }
    @Test
    public void testSpeed() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveMinPQ = new NaiveMinPQ<>();
        int O = 100000;
        testAddSpeedHelper(O);
        testContainsSpeedHelper(O);
        testRemoveSmallestSpeedHelper(O);
    }
}
