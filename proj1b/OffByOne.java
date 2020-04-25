public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int delta = x - y;
        return ((delta == 1) || (delta == -1));
    }
    public static void main(String[] args) {
        OffByOne offByOne = new OffByOne();
        System.out.println(offByOne.equalChars('%', '&'));
    }
}
