import org.junit.Test;
import static org.junit.Assert.*;
public class TestUnionFind {
    @Test
    public void testUnionFind() {
        UnionFind unionFind = new UnionFind(16);
        unionFind.union(0,1);
        unionFind.union(0, 2);
        unionFind.union(0, 3);
        unionFind.union(0, 4);
        assertTrue(!unionFind.connected(4, 5));
        assertTrue(unionFind.connected(3, 4));
        unionFind.union(1,5);
        unionFind.union(1,6);
        unionFind.union(1,7);
        unionFind.union(5,11);
        unionFind.union(5,12);
        unionFind.union(11, 15);
        unionFind.union(6, 13);
        unionFind.union(2,8);
        unionFind.union(2,9);
        unionFind.union(8, 14);
        unionFind.union(3, 10);
        unionFind.connected(15, 10);
        unionFind.connected(14, 13);
        assertEquals(unionFind.sizeOf(0), 16);
    }
}
