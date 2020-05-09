package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        List<List<Oomage>> lists = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            lists.add(new ArrayList<Oomage>());
        }

        for (Oomage oomage : oomages) {
            int bucketNum = (oomage.hashCode() & 0x7fffffff) % M;
            if (!lists.get(bucketNum).contains(oomage)) {
                lists.get(bucketNum).add(oomage);
            }
        }
        double lowerBound = oomages.size() / 50;
        double upperBound = oomages.size() / 2.5;
        for (List<Oomage> item : lists) {
            if (item.size() <= lowerBound || item.size() >= upperBound) {
                return false;
            }
        }
        return true;
    }
}
