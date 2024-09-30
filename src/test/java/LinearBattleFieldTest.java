import org.junit.Before;
import org.junit.Test;

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
    public void testInitialFieldIsEmpty() {
        char[] expectedField = {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        assertArrayEquals(expectedField, field.getField());
    }

    @Test
    public void testPlaceSingleShip() {
        field.placeSingleShip();
        int shipCount = 0;
        for (char c : field.getField()) {
            if (c == 'S') {
                shipCount++;
            }
        }
        assertEquals(1, shipCount);  // Ожидаем, что ровно один корабль будет на поле
    }

    @Test
    public void testShootAtPosition() {
        field.placeSingleShip();  // Располагаем корабль
        int targetPosition = 5;
        String result = field.shootAt(targetPosition + 1);  // Ввод от 1 до 10, поэтому прибавляем 1
        assertTrue(result.equals("hit") || result.equals("промахнулись"));  // Ожидаем либо попадание, либо промах
    }

    @Test
    public void testHitAndMissMarkers() {
        field.placeSingleShip();
        int shipPosition = -1;

        // Находим, где расположен корабль
        for (int i = 0; i < field.getSize(); i++) {
            if (field.getField()[i] == 'S') {
                shipPosition = i;
                break;
            }
        }

        String hitResult = field.shootAt(shipPosition + 1);
        assertEquals("hit", hitResult);
        assertEquals('x', field.getField()[shipPosition]);

        int missPosition = (shipPosition + 1) % field.getSize();
        String missResult = field.shootAt(missPosition + 1);
        assertEquals("промахнулись", missResult);
        assertEquals('*', field.getField()[missPosition]);
    }

    @Test
    public void testShipSunkMessage() {
        field.placeSingleShip();
        int shipPosition = -1;

        // Находим, где расположен корабль
        for (int i = 0; i < field.getSize(); i++) {
            if (field.getField()[i] == 'S') {
                shipPosition = i;
                break;
            }
        }

        String result = field.shootAt(shipPosition + 1);
        assertEquals("потопили", result);  // Ожидаем сообщение "потопили"
        assertTrue(field.isGameOver());  // Проверяем, что игра завершилась
    }

    @Test
    public void testTurnCount() {
        field.placeSingleShip();
        int shipPosition = -1;

        // Находим, где расположен корабль
        for (int i = 0; i < field.getSize(); i++) {
            if (field.getField()[i] == 'S') {
                shipPosition = i;
                break;
            }
        }

        // Стреляем по нескольким позициям
        field.shootAt((shipPosition + 1) % field.getSize() + 1);  // Промах
        field.shootAt(shipPosition + 1);  // Попадание и потопление

        assertTrue(field.isGameOver());  // Проверяем, что игра завершилась
        assertEquals(2, field.getTurnCount());  // Ожидаем, что было сделано 2 хода
    }

    @Test
    public void testGameAlreadyOver() {
        field.placeSingleShip();
        int shipPosition = -1;

        // Находим, где расположен корабль
        for (int i = 0; i < field.getSize(); i++) {
            if (field.getField()[i] == 'S') {
                shipPosition = i;
                break;
            }
        }

        // Потопить корабль
        field.shootAt(shipPosition + 1);
        assertTrue(field.isGameOver());

        // Попробовать стрелять после окончания игры
        String result = field.shootAt(shipPosition + 1);
        assertEquals("игра уже завершена", result);
    }
}
