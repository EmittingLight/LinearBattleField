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
        assertTrue(result.equals("hit") || result.equals("miss"));  // Ожидаем либо попадание, либо промах
    }
}

