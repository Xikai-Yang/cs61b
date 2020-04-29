package creatures;

import huglife.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestClorus {
    @Test
    public void testAttack() {

        Clorus c = new Clorus(4.4);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Plip());
        surrounded.put(Direction.BOTTOM, new Empty());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Impassible());
        Action actual = c.chooseAction(surrounded);
        Action unexpected = new Action(Action.ActionType.STAY);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.TOP);
        assertNotEquals(actual, unexpected);
        assertEquals(actual, expected);
    }

}
