package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

public class Clorus extends Creature {

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    @Override
    public void move() {
        energy = Math.max(energy - 0.03, 0);
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();

    }

    @Override
    public Creature replicate() {
        double offSpringEnergy = energy / 2;
        energy -= offSpringEnergy;
        return new Clorus(offSpringEnergy);
    }

    @Override
    public void stay() {
        energy = Math.max(energy - 0.01, 0);
    }
    public Direction chooseDirection(int number) {
        switch (number) {
            case 0: return Direction.TOP;
            case 1: return Direction.BOTTOM;
            case 2: return Direction.LEFT;
            case 3: return Direction.RIGHT;
        }
        return null;
    }
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        for (Direction direction : neighbors.keySet()) {
            if (!neighbors.get(direction).name().equals("empty")) {
                emptyNeighbors.push(direction);
            }
            if (neighbors.get(direction).name().equals("plip")) {
                plipNeighbors.push(direction);
            }
        }
        // rule 1
        if (emptyNeighbors.size() == 4) {
            return new Action(Action.ActionType.STAY);
        }
        Random random = new Random();
        int dirNumber = random.nextInt(4);
        if (plipNeighbors.size() > 0) {
            while (!plipNeighbors.contains(chooseDirection(dirNumber))) {
                dirNumber = random.nextInt(4);
            }
            return new Action(Action.ActionType.ATTACK, chooseDirection(dirNumber));
        }

        // choose a random empty space
        while (emptyNeighbors.contains(chooseDirection(dirNumber))) {
            dirNumber = random.nextInt(4);
        }
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, chooseDirection(dirNumber));
        } else {
            return new Action(Action.ActionType.MOVE, chooseDirection(dirNumber));
        }
    }

    @Override
    public Color color() {
        return color(34, 0, 231);
    }
}
