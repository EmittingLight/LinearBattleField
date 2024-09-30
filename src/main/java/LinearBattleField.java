import java.util.Random;

public class LinearBattleField {
    private static final int SIZE = 10;
    private char[] field;
    private Random random;

    public LinearBattleField() {
        field = new char[SIZE];
        for (int i = 0; i < SIZE; i++) {
            field[i] = '.';
        }
        random = new Random();
    }

    public int getSize() {
        return SIZE;
    }

    public char[] getField() {
        return field;
    }

    public void placeSingleShip() {
        int position = random.nextInt(SIZE);
        field[position] = 'S';
    }
}



