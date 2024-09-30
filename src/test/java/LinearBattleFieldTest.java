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
        String result = field.shootAt(targetPosition);
        assertTrue(result.equals("hit") || result.equals("промахнулись"));  // Ожидаем либо попадание, либо промах
    }

    @Test
    public void testHitAndMissMarkers() {
        // Размещаем корабль на определенной позиции для теста
        field.placeSingleShip();
        int shipPosition = -1;

        // Находим, где расположен корабль
        for (int i = 0; i < field.getSize(); i++) {
            if (field.getField()[i] == 'S') {
                shipPosition = i;
                break;
            }
        }

        // Проверка на попадание по кораблю
        String hitResult = field.shootAt(shipPosition);
        //assertEquals("hit", hitResult);  // Должно быть "hit" при попадании
        assertEquals('x', field.getField()[shipPosition]);  // Должен быть "x" при попадании

        // Проверка на промах (стреляем в другую позицию)
        int missPosition = (shipPosition + 1) % field.getSize();  // Любая другая позиция, чтобы промахнуться
        String missResult = field.shootAt(missPosition);
        assertEquals("промахнулись", missResult);  // Должно быть "промахнулись" при промахе
        assertEquals('*', field.getField()[missPosition]);  // Должен быть "*" при промахе
    }

    @Test
    public void testShipSunkMessage() {
        // Размещаем корабль и проверяем сообщения после потопления
        field.placeSingleShip();
        int shipPosition = -1;

        // Находим, где расположен корабль
        for (int i = 0; i < field.getSize(); i++) {
            if (field.getField()[i] == 'S') {
                shipPosition = i;
                break;
            }
        }

        // Проверяем попадание и получение сообщения "потопили"
        String result = field.shootAt(shipPosition);
        assertEquals("потопили", result);  // Ожидаем сообщение "потопили"
    }
}
