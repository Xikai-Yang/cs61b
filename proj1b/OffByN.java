public class OffByN implements CharacterComparator {
    private int n;
    public OffByN(int n_) {
        this.n = n_;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int delta = x - y;
        if ((delta == n) || (delta == -n)) {
            return true;
        }
        return false;
    }

}
