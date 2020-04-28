package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Hexagon {
    private static final int WIDTH = 60;
    private static final int LENGTH = 40;
    private static int[] calHexagon(int size) {
        int totalLength = size * 2;
        int[] widthArray = new int[totalLength];
        int tempSize = size;
        for (int i = 0; i < size; i++,tempSize++) {
            widthArray[i] = tempSize;
        }
        tempSize--;
        for (int i = size; i < totalLength; i++, tempSize--) {
            widthArray[i] = tempSize;
        }
        return widthArray;
    }
    private static void drawFirstLine(TETile[][] world, int size,
                                      int locationX, int locationY, TETile tile) {
        if (size % 2 == 0) {
            // even number
            int half = size / 2;
            for (int i = locationX - half + 1; i <= locationX + half ; i++) {
                world[i][locationY] = tile;
            }
        } else {
            int half = size / 2;
            for (int i = locationX - half; i <= locationX + half ; i++) {
                world[i][locationY] = tile;
            }
        }
    }
    private static void Init(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }
    public static void drawHexagon(TETile[][] world, int size,
                                    int locationX, int locationY, TETile tile) {

        int totalLength = size * 2;
        int[] widthArray = calHexagon(size);
        int tempSize = size;
        for (int i = locationY; i >= locationY - size + 1; i--, tempSize += 2) {
            drawFirstLine(world, tempSize, locationX, i, tile);
        }
        tempSize -= 2;
        for (int i = locationY - size; i >= locationY - totalLength + 1 ; i--, tempSize -= 2) {
            drawFirstLine(world, tempSize, locationX, i, tile);
        }
    }





    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,LENGTH);
        TETile[][] world = new TETile[WIDTH][LENGTH];
        Init(world);
        drawHexagon(world, 4, 15, 25, Tileset.FLOWER);
        ter.renderFrame(world);
    }
}
