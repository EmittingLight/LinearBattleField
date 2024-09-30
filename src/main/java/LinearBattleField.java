public class LinearBattleField {
    private static final int SIZE = 10;
    private char[] field;

    public LinearBattleField() {
        field = new char[SIZE];
        for (int i = 0; i < SIZE; i++) {
            field[i] = '.';
        }
    }

    public int getSize() {
        return SIZE;
    }

    public char[] getField() {
        return field;
    }
}


