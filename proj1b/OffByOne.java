public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int delta = x - y;
        return ((delta == 1) || (delta == -1));
    }
}
