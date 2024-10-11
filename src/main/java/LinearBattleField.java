import java.util.Random;

import java.util.Random;

public class LinearBattleField {
    private static final int SIZE = 10;
    private char[] playerField;        // Поле для отображения состояния игрока
    private char[] computerField;      // Поле для отображения состояния компьютера
    private char[] playerHiddenField;  // Скрытое поле игрока (корабль игрока)
    private char[] computerHiddenField; // Скрытое поле компьютера (корабль компьютера)
    private Random random;
    private boolean gameOver;
    private int humanTurnCount;
    private int computerTurnCount;
    private String winner; // Определяет, кто победил: "человек" или "компьютер"

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
        humanTurnCount = 0;
        computerTurnCount = 0;
        winner = "";
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
        // Размещаем корабль на скрытом поле игрока
        playerHiddenField[position] = 'S';
        // Выводим для отладки, что корабль игрока размещён
        System.out.println("Корабль игрока размещен на позиции: " + (position + 1));
    }

    public void placeComputerShip() {
        int position = random.nextInt(SIZE);
        computerHiddenField[position] = 'S';  // Компьютер размещает корабль на скрытом поле

        // Отладочный вывод для отображения скрытого поля компьютера
        System.out.println("Корабль компьютера размещен на позиции: " + (position + 1));
        System.out.println("Скрытое поле компьютера:");
        displayField(computerHiddenField);  // Вывод скрытого поля компьютера
    }

    // Метод для отображения текущего состояния поля
    private static void displayField(char[] field) {
        for (char cell : field) {
            System.out.print(cell + " ");
        }
        System.out.println();
    }

    public String playerShootAt(int userInput) {
        humanTurnCount++; // Увеличиваем счетчик ходов человека
        String result = shootAt(userInput, computerHiddenField, computerField);
        if (result.equals("потопили")) {
            gameOver = true;
            winner = "человек";
        }
        return result;
    }

    public String computerShootAt() {
        int computerInput;
        do {
            computerInput = random.nextInt(SIZE);
        } while (playerField[computerInput] == 'x' || playerField[computerInput] == '*');  // Проверяем, что сюда еще не стреляли

        computerTurnCount++; // Увеличиваем счетчик ходов компьютера

        System.out.println("Компьютер стреляет в ячейку: " + (computerInput + 1));  // Отладочное сообщение

        // Стреляем по скрытому полю игрока (playerHiddenField)
        String result = shootAt(computerInput + 1, playerHiddenField, playerField);

        System.out.println("Результат выстрела компьютера: " + result);  // Отладочное сообщение

        if (result.equals("потопили")) {
            gameOver = true;
            winner = "компьютер";
        }
        return result;
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

        System.out.println("Позиция выстрела: " + userInput);  // Отладочное сообщение

        if (targetHiddenField[position] == 'S') {
            targetHiddenField[position] = 'x';  // Попадание
            visibleField[position] = 'x';       // Отображаем попадание
            System.out.println("Попадание в корабль!");  // Отладочное сообщение
            if (isShipSunk(targetHiddenField)) {
                return "потопили";
            }
            return "hit";
        } else if (visibleField[position] == '.') {
            visibleField[position] = '*';  // Промах
            System.out.println("Промах.");  // Отладочное сообщение
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

    public String getWinner() {
        return winner;
    }

    public int getHumanTurnCount() {
        return humanTurnCount;
    }

    public int getComputerTurnCount() {
        return computerTurnCount;
    }

    public char[] getComputerHiddenField() {
        return computerHiddenField;
    }

}
