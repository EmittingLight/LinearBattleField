import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        LinearBattleField game = new LinearBattleField();

        // Располагаем корабль
        game.placeSingleShip();

        System.out.println("Добро пожаловать в Линейный Морской Бой!");
        System.out.println("На поле длиной 10 клеток расположен корабль. Ваша задача - потопить его.");
        System.out.println("Играют два игрока: человек и компьютер.");
        System.out.println("Введите номер ячейки от 1 до 10 для выстрела.");

        // Пока игра не закончена
        boolean isHumanTurn = true;

        while (!game.isGameOver()) {
            displayField(game.getField());

            if (isHumanTurn) {
                // Ход человека
                System.out.print("Человек, введите номер ячейки для выстрела (1-10): ");
                int userInput = scanner.nextInt();
                String result = game.shootAt(userInput);
                System.out.println("Человек: " + result);
            } else {
                // Ход компьютера
                int computerInput;
                do {
                    computerInput = random.nextInt(game.getSize()) + 1;  // Компьютер стреляет по случайной ячейке от 1 до 10
                } while (game.getField()[computerInput - 1] == 'x' || game.getField()[computerInput - 1] == '*');  // Проверяем, чтобы компьютер не стрелял в уже пораженные ячейки

                String result = game.shootAt(computerInput);
                System.out.println("Компьютер стреляет в ячейку " + computerInput + ": " + result);
            }

            // Переход хода к следующему игроку
            isHumanTurn = !isHumanTurn;
        }

        // После окончания игры выводим информацию о количестве ходов
        System.out.println("Игра завершена! Корабль потоплен.");
        System.out.println("Количество сделанных ходов: " + game.getTurnCount());

        // Показываем окончательное состояние поля
        displayField(game.getField());
    }

    // Метод для отображения текущего состояния поля
    private static void displayField(char[] field) {
        for (char cell : field) {
            System.out.print(cell + " ");
        }
        System.out.println();
    }
}
