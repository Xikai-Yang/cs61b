public class UnionFind {
    // TODO - Add instance variables?
    int[] data = null;
    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        // TODO
        if (vertex >= data.length || vertex < 0) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        // TODO
        int root = find(v1);
        return -1 * data[root];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // TODO
        validate(v1);
        return data[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        return rootV1 == rootV2;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a
       vertex with itself or vertices that are already connected should not
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        // TODO
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        if (rootV1 == rootV2) {
            return;
        }
        int sizeV1 = data[rootV1] * -1;
        int sizeV2 = data[rootV2] * -1;
        if (sizeV1 < sizeV2) {
            data[rootV2] += data[rootV1];
            data[rootV1] = rootV2;
        } else {
            data[rootV1] += data[rootV2];
            data[rootV2] = rootV1;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        // TODO
        validate(vertex);
        int cur = vertex;
        while (data[cur] >= 0) {
            cur = data[cur];
        }
        // path compression
        int temp = -1;
        while (data[vertex] >= 0) {
            temp = data[vertex];
            data[vertex] = cur;
            vertex = temp;
        }
        return cur;
    }

}