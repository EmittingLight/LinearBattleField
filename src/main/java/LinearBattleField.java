import java.util.Random;

import java.util.Random;

public class LinearBattleField {
    private static final int SIZE = 10;
    private char[] field;        // Поле, видимое игроку
    private char[] hiddenField;  // Поле, скрытое от игрока (содержит положение корабля)
    private Random random;
    private boolean gameOver;
    private int turnCount;

    public LinearBattleField() {
        field = new char[SIZE];
        hiddenField = new char[SIZE];
        for (int i = 0; i < SIZE; i++) {
            field[i] = '.';
            hiddenField[i] = '.';  // Скрытое поле также инициализируется пустым значением
        }
        random = new Random();
        gameOver = false;
        turnCount = 0;
    }

    public int getSize() {
        return SIZE;
    }

    public char[] getField() {
        return field;
    }

    public void placeSingleShip() {
        int position = random.nextInt(SIZE);
        hiddenField[position] = 'S';  // Располагаем корабль на скрытом поле
    }

    public String shootAt(int userInput) {
        if (gameOver) {
            return "игра уже завершена";  // Если игра уже закончена, нельзя стрелять
        }

        // Преобразуем ввод от пользователя (1-10) в индекс массива (0-9)
        int position = convertInputToIndex(userInput);

        if (position < 0 || position >= SIZE) {
            return "некорректный ввод";  // Если позиция вне диапазона, вернуть сообщение об ошибке
        }

        turnCount++;  // Увеличиваем счетчик ходов на каждом выстреле

        if (hiddenField[position] == 'S') {
            field[position] = 'x';  // Попадание отображается игроку
            hiddenField[position] = 'x';  // Обновляем скрытое поле
            if (isShipSunk()) {
                gameOver = true;  // Игра завершена, корабль потоплен
                return "потопили";
            }
            return "hit";
        } else if (field[position] == '.') {
            field[position] = '*';  // Промах отображается игроку
            return "промахнулись";
        } else {
            return "уже стреляли сюда";  // Попытка выстрелить в уже пораженную ячейку
        }
    }

    private int convertInputToIndex(int userInput) {
        return userInput - 1;  // Преобразование из диапазона (1-10) в (0-9)
    }

    private boolean isShipSunk() {
        for (char cell : hiddenField) {
            if (cell == 'S') {
                return false;  // Корабль еще не потоплен
            }
        }
        return true;  // Корабль потоплен
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getTurnCount() {
        return turnCount;
    }
}

