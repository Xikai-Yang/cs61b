package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Tesselation {
    private static final int WIDTH = 100;
    private static final int LENGTH = 100;
    private static final int seed = 1234;
    private static final Random random = new Random(seed);
    private static TETile randomTile() {
        int type = random.nextInt(6);
        switch (type) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.TREE;
            case 4: return Tileset.MOUNTAIN;
            case 5: return Tileset.SAND;
            default: return Tileset.NOTHING;
        }
    }

    private static void drawVerticalBoxes(TETile[][] world, int size,
                                          int locationX, int locationY, int number) {
        for (int i = 0; i < number; i++) {
            Hexagon.drawHexagon(world, size, locationX, locationY, randomTile());
            locationY -= (size * 2);
        }
    }
    private static void calcTopRightPosition(int size, int locationX, int locationY, int[] cache) {
        // location refer to the top location of the previous column.
        cache[1] = locationY + size;
        int half = size / 2;
        if (size % 2 == 0) {
            cache[0] = locationX + half + size;
        } else {
            cache[0] = locationX + half + size + 1;
        }
    }
    private static void calcBottomRightPosition(int size, int locationX, int locationY, int[] cache) {
        cache[1] = locationY - size;
        int half = size / 2;
        if (size % 2 == 0) {
            cache[0] = locationX + half + size;
        } else {
            cache[0] = locationX + half + size + 1;
        }
    }
    public static void drawTesselation(TETile[][] world,int size, int locationX, int locationY) {
        int number = size;
        int[] cache = {0, 0};
        for (int i = 0; i < size - 1; i++) {
            drawVerticalBoxes(world, size, locationX, locationY, number);
            calcTopRightPosition(size, locationX, locationY, cache);
            locationX = cache[0];
            locationY = cache[1];
            number++;
        }
        drawVerticalBoxes(world, size, locationX, locationY, number);
        for (int i = 0; i < size - 1; i++) {
            calcBottomRightPosition(size, locationX, locationY, cache);
            locationX = cache[0];
            locationY = cache[1];
            number--;
            drawVerticalBoxes(world, size, locationX, locationY, number);
        }


    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,LENGTH);
        TETile[][] world = new TETile[WIDTH][LENGTH];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        int[] cache = {0, 0};
        int locationX = 40;
        int locationY = 60;
        int size = 3;
        drawTesselation(world, size,locationX, locationY);
        ter.renderFrame(world);
    }
}
