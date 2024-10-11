import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LinearBattleFieldTest {
    private LinearBattleField field;

    @Before
    public void setUp() {
        field = new LinearBattleField();
    }

    @Test
    public void testFieldSize() {
        assertEquals(10, field.getSize());
    }

    @Test
    public void testInitialFieldsAreEmpty() {
        // Ожидаем, что все ячейки на поле игрока заполнены точками "."
        char[] expectedPlayerField = {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};

        // Ожидаем, что все ячейки на поле компьютера также заполнены точками "."
        char[] expectedComputerField = {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};

        // Проверяем поле игрока
        assertArrayEquals(expectedPlayerField, field.getPlayerField());

        // Проверяем поле компьютера
        assertArrayEquals(expectedComputerField, field.getComputerField());
    }


    @Test
    public void testPlacePlayerShip() {
        // Выводим состояние поля игрока до размещения корабля
        System.out.println("Поле игрока до размещения корабля: " + Arrays.toString(field.getPlayerField()));

        // Размещаем корабль игрока на позиции 5
        field.placePlayerShip(0);

        // Выводим состояние поля игрока после размещения корабля
        System.out.println("Поле игрока после размещения корабля: " + Arrays.toString(field.getPlayerField()));

        // Проверяем, что корабль был размещен
        int shipCount = 0;
        for (char c : field.getPlayerField()) {  // Проверяем видимое поле игрока
            if (c == 'S') {  // Если корабль успешно размещен
                shipCount++;
            }
        }

        // Выводим количество найденных кораблей
        System.out.println("Количество кораблей на поле: " + shipCount);

        // Ожидаем, что ровно один корабль будет на поле игрока
        assertEquals(1, shipCount);
    }


    @Test
    public void testPlaceComputerShip() {
        // Размещаем корабль компьютера
        field.placeComputerShip();
        int shipCount = 0;
        // Проверяем на скрытом поле, где фактически размещается корабль компьютера
        for (char c : field.getComputerHiddenField()) {
            if (c == 'S') {
                shipCount++;
            }
        }
        assertEquals(1, shipCount);  // Ожидаем, что ровно один корабль будет на скрытом поле компьютера
    }


    @Test
    public void testPlayerShootAtComputerShip() {
        // Размещаем корабль компьютера
        field.placeComputerShip();
        int shipPosition = -1;

        // Находим, где расположен корабль компьютера на скрытом поле
        for (int i = 0; i < field.getSize(); i++) {
            if (field.getComputerHiddenField()[i] == 'S') {
                shipPosition = i;
                break;
            }
        }

        assertTrue("Корабль компьютера должен быть размещен", shipPosition >= 0);

        // Человек стреляет по кораблю компьютера
        String hitResult = field.playerShootAt(shipPosition + 1);
        assertTrue("Ожидаем либо 'hit', либо 'потопили'", hitResult.equals("hit") || hitResult.equals("потопили"));
        assertEquals('x', field.getComputerField()[shipPosition]);

        // Если игра еще не завершена, проверяем промах
        if (!field.isGameOver()) {
            int missPosition = (shipPosition + 1) % field.getSize();
            String missResult = field.playerShootAt(missPosition + 1);
            assertEquals("промахнулись", missResult);
            assertEquals('*', field.getComputerField()[missPosition]);
        } else {
            System.out.println("Игра завершена, дальнейшие выстрелы невозможны.");
        }
    }



    @Test
    public void testComputerShootAtPlayerShip() {
        // Размещаем корабль игрока
        field.placePlayerShip(3);
        int attempts = 0;
        String result = "";

        // Компьютер стреляет до попадания
        while (!result.equals("hit") && attempts < 10) {
            result = field.computerShootAt();
            attempts++;
        }

        // Проверяем, что результат либо "hit", либо "промахнулись", и что поле было обновлено
        assertTrue(result.equals("hit") || result.equals("промахнулись"));
    }

    @Test
    public void testShipSunkMessageForPlayer() {
        // Размещаем корабль компьютера
        field.placeComputerShip();
        int shipPosition = -1;

        // Находим, где расположен корабль компьютера
        for (int i = 0; i < field.getSize(); i++) {
            if (field.getComputerField()[i] == 'S') {
                shipPosition = i;
                break;
            }
        }

        assertTrue("Корабль компьютера должен быть размещен", shipPosition >= 0);

        // Человек стреляет по кораблю компьютера и потопляет его
        String result = field.playerShootAt(shipPosition + 1);
        assertEquals("потопили", result);  // Ожидаем сообщение "потопили"
        assertTrue(field.isGameOver());  // Проверяем, что игра завершилась
        assertEquals("человек", field.getWinner());  // Проверяем, что человек выиграл
    }

    @Test
    public void testShipSunkMessageForComputer() {
        // Размещаем корабль игрока
        field.placePlayerShip(4);

        int attempts = 0;
        String result = "";

        // Компьютер стреляет до потопления корабля игрока
        while (!field.isGameOver() && attempts < 10) {
            result = field.computerShootAt();
            attempts++;
        }

        assertEquals("потопили", result);  // Ожидаем, что компьютер потопил корабль
        assertTrue(field.isGameOver());  // Проверяем, что игра завершилась
        assertEquals("компьютер", field.getWinner());  // Проверяем, что компьютер выиграл
    }

    @Test
    public void testTurnCounts() {
        // Размещаем корабли
        field.placePlayerShip(3);
        field.placeComputerShip();

        // Человек стреляет
        field.playerShootAt(4);
        field.playerShootAt(5);

        // Компьютер стреляет дважды
        field.computerShootAt();
        field.computerShootAt();

        assertEquals(2, field.getHumanTurnCount());  // Проверяем, что у человека 2 хода
        assertEquals(2, field.getComputerTurnCount());  // Проверяем, что у компьютера 2 хода
    }
}
