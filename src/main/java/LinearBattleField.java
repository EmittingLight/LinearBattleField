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

    public String shootAt(int position) {
        if (field[position] == 'S') {
            field[position] = 'x';  // Попадание
            if (isShipSunk()) {
                return "потопили";  // Корабль полностью потоплен
            }
            return "hit";
        } else if (field[position] == '.') {
            field[position] = '*';  // Промах
            return "промахнулись";  // Сообщение о промахе
        } else {
            return "уже стреляли сюда";  // Попытка выстрелить в уже пораженную ячейку
        }
    }

    private boolean isShipSunk() {
        for (char cell : field) {
            if (cell == 'S') {
                return false;  // Корабль еще не потоплен
            }
        }
        return true;  // Корабль потоплен
    }
}
