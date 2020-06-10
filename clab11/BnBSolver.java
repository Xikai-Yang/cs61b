import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    private List<Bear> bearList;
    private List<Bed> bedList;

    private void N2Solver() {
        int length = bearList.size();
        for (int i = 0; i < length; i++) {
            Bear bear = bearList.get(i);
            int index = i;
            Bed bed = bedList.get(i);
            for (int j = i; j < length; j++) {
                bed = bedList.get(j);
                if (bed.compareTo(bear) == 0) {
                    index = j;
                    break;
                }
            }
            // swap two beds
            bedList.set(index, bedList.get(i));
            bedList.set(i, bed);
        }

    }

    private void partitionBearList(List<Bear> unsorted, Bed pivot, List<Bear> less,
                                   List<Bear> equal, List<Bear> greater) {
        for (Bear bear : unsorted) {
            int cmp = bear.compareTo(pivot);
            if (cmp < 0) {
                less.add(bear);
            }
            if (cmp > 0) {
                greater.add(bear);
            }
            if (cmp == 0) {
                equal.add(bear);
            }
        }
    }

    private void partitionBedList(List<Bed> unsorted, Bear pivot, List<Bed> less,
                                  List<Bed> equal, List<Bed> greater) {
        for (Bed bed : unsorted) {
            int cmp = bed.compareTo(pivot);
            if (cmp < 0) {
                less.add(bed);
            }
            if (cmp > 0) {
                greater.add(bed);
            }
            if (cmp == 0) {
                equal.add(bed);
            }
        }
    }



    private <T> List<T> concatenate(List<T> List1, List<T> List2) {
        List<T> list = new ArrayList<>();
        for (T item : List1) {
            list.add(item);
        }
        for (T item: List2) {
            list.add(item);
        }
        return list;
    }


    private Pair<List<Bear>, List<Bed>> helper(List<Bear> bears, List<Bed> beds) {
        if (bears.isEmpty() || beds.isEmpty()) {
            return new Pair<>(new ArrayList<>(), new ArrayList<>());
        }
        Bear pivotBear = bears.get(0);
        List<Bed> lessBedList = new ArrayList<>();
        List<Bed> greaterBedList = new ArrayList<>();
        List<Bed> equalBedList = new ArrayList<>();
        partitionBedList(beds, pivotBear, lessBedList, equalBedList, greaterBedList);

        Bed pivotBed = equalBedList.get(0);
        List<Bear> lessBearList = new ArrayList<>();
        List<Bear> equalBearList = new ArrayList<>();
        List<Bear> greaterBearList = new ArrayList<>();
        partitionBearList(bears, pivotBed, lessBearList, equalBearList, greaterBearList);

        Pair<List<Bear>, List<Bed>> lessPair = helper(lessBearList, lessBedList);
        Pair<List<Bear>, List<Bed>> greaterPair = helper(greaterBearList, greaterBedList);
        Pair<List<Bear>, List<Bed>> equalPair = new Pair<>(equalBearList, equalBedList);

        List<Bear> bearList = concatenate(concatenate(lessPair.first(), equalPair.first()), greaterPair.first());
        List<Bed> bedList = concatenate(concatenate(lessPair.second(), equalPair.second()), greaterPair.second());

        return new Pair<>(bearList, bedList);

    }

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        // TODO: Fix me.
        Pair<List<Bear>, List<Bed>> pair = helper(bears, beds);
        this.bearList = pair.first();
        this.bedList = pair.second();
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // TODO: Fix me.
        return this.bearList;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        return this.bedList;
    }
}
