package scene;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AreaTest {

    @Test
    void testCreation() {
        Area a = new Area(80, 40, 7L, "Area1");
        assertEquals(7L, a.getId());
        assertEquals("Area1", a.getName());
        assertEquals(80, a.getWidth());
        assertEquals(40, a.getHeight());
        assertEquals('.', a.getEmptyChar());
        assertEquals('@', a.getFullChar());
        assertTrue(a.getLayers().isEmpty());
    }

    @Test
    void testGridDimensions() {
        Area a = new Area(10, 5, 1L, "A");
        char[][] grid = a.getGrid();
        assertEquals(5, grid.length);
        assertEquals(10, grid[0].length);
    }

    @Test
    void testGridRempliDuCharacterVide() {
        Area a = new Area(3, 2, 1L, "A");
        char[][] g = a.getGrid();
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals('.', g[y][x]);
            }
        }
    }

    @Test
    void testClearRestaureCharacterVide() {
        Area a = new Area(3, 2, 1L, "A");
        a.getGrid()[0][0] = '#';
        a.getGrid()[1][2] = 'X';
        a.clear();
        assertEquals('.', a.getGrid()[0][0]);
        assertEquals('.', a.getGrid()[1][2]);
    }

    @Test
    void testClearUtiliseEmptyCharCourant() {
        Area a = new Area(2, 2, 1L, "A");
        a.setEmptyChar(' ');
        a.clear();
        assertEquals(' ', a.getGrid()[0][0]);
    }

    @Test
    void testAddLayer() {
        Area a = new Area(5, 5, 1L, "A");
        Layer l = new Layer(1L, "L1");
        a.addLayer(l);
        assertEquals(1, a.getLayers().size());
        assertSame(l, a.getLayers().get(0));
    }

    @Test
    void testRemoveLayer() {
        Area a = new Area(5, 5, 1L, "A");
        Layer l = new Layer(1L, "L1");
        a.addLayer(l);
        assertTrue(a.removeLayer(l));
        assertTrue(a.getLayers().isEmpty());
    }
}
