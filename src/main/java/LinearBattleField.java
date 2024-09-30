import java.util.Random;

public class LinearBattleField {
    private static final int SIZE = 10;
    private char[] playerField;        // Поле для отображения состояния игрока
    private char[] computerField;      // Поле для отображения состояния компьютера
    private char[] playerHiddenField;  // Скрытое поле игрока (корабль игрока)
    private char[] computerHiddenField; // Скрытое поле компьютера (корабль компьютера)
    private Random random;
    private boolean gameOver;
    private int turnCount;

    public LinearBattleField() {
        playerField = new char[SIZE];
        computerField = new char[SIZE];
        playerHiddenField = new char[SIZE];
        computerHiddenField = new char[SIZE];

        for (int i = 0; i < SIZE; i++) {
            playerField[i] = '.';
            computerField[i] = '.';
            playerHiddenField[i] = '.';
            computerHiddenField[i] = '.';
        }

        random = new Random();
        gameOver = false;
        turnCount = 0;
    }

    public int getSize() {
        return SIZE;
    }

    public char[] getPlayerField() {
        return playerField;
    }

    public char[] getComputerField() {
        return computerField;
    }

    public void placePlayerShip(int position) {
        if (position < 0 || position >= SIZE) {
            throw new IllegalArgumentException("Некорректная позиция для корабля.");
        }
        playerHiddenField[position] = 'S';  // Игрок размещает корабль на скрытом поле
    }

    public void placeComputerShip() {
        int position = random.nextInt(SIZE);
        computerHiddenField[position] = 'S';  // Компьютер размещает корабль на скрытом поле
    }

    public String playerShootAt(int userInput) {
        return shootAt(userInput, computerHiddenField, computerField);
    }

    public String computerShootAt() {
        int computerInput;
        do {
            computerInput = random.nextInt(SIZE);
        } while (playerField[computerInput] == 'x' || playerField[computerInput] == '*');

        return shootAt(computerInput + 1, playerHiddenField, playerField);
    }

    private String shootAt(int userInput, char[] targetHiddenField, char[] visibleField) {
        if (gameOver) {
            return "игра уже завершена";  // Если игра уже закончена, нельзя стрелять
        }

        // Преобразуем ввод от пользователя (1-10) в индекс массива (0-9)
        int position = convertInputToIndex(userInput);

        if (position < 0 || position >= SIZE) {
            return "некорректный ввод";  // Если позиция вне диапазона, вернуть сообщение об ошибке
        }

        turnCount++;  // Увеличиваем счетчик ходов на каждом выстреле

        if (targetHiddenField[position] == 'S') {
            targetHiddenField[position] = 'x';  // Попадание
            visibleField[position] = 'x';       // Отображаем попадание
            if (isShipSunk(targetHiddenField)) {
                gameOver = true;  // Игра завершена, корабль потоплен
                return "потопили";
            }
            return "hit";
        } else if (visibleField[position] == '.') {
            visibleField[position] = '*';  // Промах
            return "промахнулись";
        } else {
            return "уже стреляли сюда";  // Попытка выстрелить в уже пораженную ячейку
        }
    }

    private int convertInputToIndex(int userInput) {
        return userInput - 1;  // Преобразование из диапазона (1-10) в (0-9)
    }

    private boolean isShipSunk(char[] targetHiddenField) {
        for (char cell : targetHiddenField) {
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
