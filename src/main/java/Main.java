import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Бесконечный цикл, чтобы игра могла перезапускаться
        boolean continuePlaying = true;

        while (continuePlaying) {
            LinearBattleField game = new LinearBattleField();

            // Человек выбирает позицию для своего корабля
            System.out.println("Добро пожаловать в Линейный Морской Бой!");
            System.out.println("На поле длиной 10 клеток вы должны разместить свой корабль.");
            System.out.println("Введите номер ячейки от 1 до 10, чтобы разместить корабль:");

            int playerShipPosition = -1;
            while (true) {
                System.out.print("Введите номер ячейки для размещения корабля (1-10): ");
                playerShipPosition = scanner.nextInt();
                if (playerShipPosition >= 1 && playerShipPosition <= 10) {
                    break;  // Введена корректная позиция
                } else {
                    System.out.println("Некорректный ввод. Пожалуйста, введите число от 1 до 10.");
                }
            }

            // Размещаем корабль человека на скрытом поле
            game.placePlayerShip(playerShipPosition - 1);

            // Компьютер размещает свой корабль на скрытом поле
            game.placeComputerShip();

            System.out.println("Ваш корабль размещен. Теперь игра начинается!");
            System.out.println("Играют два игрока: человек и компьютер.");
            System.out.println("Введите номер ячейки от 1 до 10 для выстрела.");

            // Пока игра не закончена
            boolean isHumanTurn = true;

            while (!game.isGameOver()) {
                if (isHumanTurn) {
                    // Ход человека
                    System.out.print("Человек, введите номер ячейки для выстрела (1-10): ");
                    int userInput = scanner.nextInt();
                    String result = game.playerShootAt(userInput);
                    System.out.println("Человек: " + result);
                } else {
                    // Ход компьютера
                    String result = game.computerShootAt();
                    System.out.println("Компьютер стреляет: " + result);
                }

                // Переход хода к следующему игроку
                isHumanTurn = !isHumanTurn;

                // Отображаем текущее состояние полей
                System.out.println("\nСостояние поля человека:");
                displayField(game.getPlayerField());
                System.out.println("\nСостояние поля компьютера:");
                displayField(game.getComputerField());
            }

            // После окончания игры выводим информацию о победителе и количестве ходов
            if (game.getWinner().equals("человек")) {
                System.out.println("Игра завершена! Выйграл человек.");
                System.out.println("Компьютер проиграл.");
            } else if (game.getWinner().equals("компьютер")) {
                System.out.println("Игра завершена! Компьютер победил.");
                System.out.println("Человек проиграл.");
            }
            System.out.println("Количество ходов человека: " + game.getHumanTurnCount());
            System.out.println("Количество ходов компьютера: " + game.getComputerTurnCount());

            // Спрашиваем, хочет ли игрок продолжить игру
            System.out.println("Хотите продолжить игру? Введите 'да' или 'нет':");
            String answer = scanner.next();
            if (!answer.equalsIgnoreCase("да")) {
                continuePlaying = false;
                System.out.println("Игра завершена. Спасибо за игру!");
            }
        }
    }

    // Метод для отображения текущего состояния поля
    private static void displayField(char[] field) {
        for (char cell : field) {
            System.out.print(cell + " ");
        }
        System.out.println();
    }
}

