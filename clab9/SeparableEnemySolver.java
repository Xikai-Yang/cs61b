import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class SeparableEnemySolver {

    Graph g;

    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     */
    public boolean isSeparable() {
        // TODO: Fix me
        init();
        String start = null;
        for (String item : g.labels()) {
            start = item;
            break;
        }

        for (String item : g.labels()) {
            if (stringIntegerMap.get(item) == 0) {
                if (!bfs(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    private Set<String> labels;
    private Map<String, Integer> stringIntegerMap;
    private void init() {
        labels = g.labels();
        stringIntegerMap = new HashMap<>();
        for (String label : labels) {
            /*
            why should we do a for loop instead of one call for dfs?
            because there may exist several independent connected component
            so if we only call dfs(start) or something, then maybe we will lose some vertices
             */
            stringIntegerMap.put(label, 0);
        }

    }
    private boolean bfs(String start) {
        stringIntegerMap.put(start, 1);
        Queue<String> queue = new ArrayDeque<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            String string = queue.poll();
            int category = stringIntegerMap.get(string) * -1;
            for (String neighbor : g.neighbors(string)) {
                int ans = stringIntegerMap.get(neighbor);
                if (ans == 0) {
                    queue.add(neighbor);
                    stringIntegerMap.put(neighbor, category);
                } else {
                    if (ans != category) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */
    public static void main(String[] args) throws FileNotFoundException{
        SeparableEnemySolver solver = new SeparableEnemySolver("input/party4");
        assertEquals(false, solver.isSeparable());
    }
}
