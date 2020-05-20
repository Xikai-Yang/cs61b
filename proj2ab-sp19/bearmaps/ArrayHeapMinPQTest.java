package bearmaps;

import org.junit.Test;

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
        PrintHeapDemo.printSimpleHeapDrawing(minPQ.heap());
    }
    

}
