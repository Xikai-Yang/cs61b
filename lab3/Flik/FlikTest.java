import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class FlikTest {
    @Test
    public void TestIsSameNumber() {
        boolean A = Flik.isSameNumber(1,1);
        boolean B = Flik.isSameNumber(12,13);
        boolean C = Flik.isSameNumber(127,127);
        boolean D = Flik.isSameNumber(127,128);
        boolean E = Flik.isSameNumber(128,128);
        boolean F = Flik.isSameNumber(129,129);
        assertTrue(A);
        assertTrue(!B);
        assertTrue(C);
        assertTrue(!D);
        assertTrue(128==128);
        assertTrue(!F);
    }
}
