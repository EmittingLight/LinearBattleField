import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
}

