package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import javax.management.relation.RelationNotFoundException;
import javax.print.attribute.standard.RequestingUserName;
import java.awt.Color;
import java.util.*;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        g = 63;
        int tempRed = 99;
        int tempGreen = (int) (96 * energy + 63);
        int tempBlue = 76;
        return color(tempRed, tempGreen, tempBlue);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        // TODO
        energy = Math.max(energy - 0.15, 0);
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        // TODO
        energy = Math.min(energy + 0.2, 2.0);
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        double offerSpringEnergy = energy / 2;
        energy -= offerSpringEnergy;
        return new Plip(offerSpringEnergy);
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    private Direction chooseDirection(int number) {
        switch (number) {
            case 0: return Direction.TOP;
            case 1: return Direction.BOTTOM;
            case 2: return Direction.LEFT;
            case 3: return Direction.RIGHT;
        }
        return null;
    }


    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        boolean anyClorus = false;
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        // for () {...}
        for (Direction direction : neighbors.keySet()) {
            if (!(neighbors.get(direction).name().equals("empty"))) {
                emptyNeighbors.push(direction);
            }
        }


        if (emptyNeighbors.size() == 4) { // FIXME
            return new Action(Action.ActionType.STAY);
        }

        Random random = new Random();

        int dirNumber = random.nextInt(4);

        if (energy() >= 1) {
            dirNumber = random.nextInt(4);
            while (emptyNeighbors.contains(chooseDirection(dirNumber))) {
                dirNumber = random.nextInt(4);
            }
            return new Action(Action.ActionType.REPLICATE, chooseDirection(dirNumber));
        }
        if (neighbors.containsValue("clorus")) {
            int decision = random.nextInt(2);
            if (decision == 0) {
                return new Action(Action.ActionType.STAY);
            } else {
                int movNumber = random.nextInt(4);
                while (neighbors.containsKey(chooseDirection(movNumber))) {
                    movNumber = random.nextInt(4);
                }
                return new Action(Action.ActionType.MOVE, chooseDirection(movNumber));
            }
        }
        // Rule 2
        // HINT: randomEntry(emptyNeighbors)

        // Rule 3

        // Rule 4
        return new Action(Action.ActionType.STAY);
    }
}
