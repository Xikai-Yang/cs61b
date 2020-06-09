import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.Quick;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TestSortAlgs {


    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        tas.enqueue("Johnson");
        Queue<String> stringQueue = QuickSort.quickSort(tas);
        String benchmark = stringQueue.dequeue();
        while (!stringQueue.isEmpty()) {
            String item = stringQueue.dequeue();
            assertTrue(item.compareTo(benchmark) >= 0);
            benchmark = item;
        }

    }




    @Test
    public void testMergeSort() {
        Queue<Integer> integerQueue = new Queue<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            integerQueue.enqueue(random.nextInt(200));
        }
        Queue<Integer> newQueue = new Queue<>();
        newQueue = MergeSort.mergeSort(integerQueue);
        int benchmark = newQueue.dequeue();
        while (!newQueue.isEmpty()) {
            int item = newQueue.dequeue();
            assertTrue(item >= benchmark);
            benchmark = item;
        }
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
