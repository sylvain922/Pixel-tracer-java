package scene;

import org.junit.jupiter.api.Test;
import shape.Point;
import shape.Shape;
import static org.junit.jupiter.api.Assertions.*;

class LayerTest {

    @Test
    void testCreationParDefautVisible() {
        Layer l = new Layer(1L, "Layer 1");
        assertEquals(1L, l.getId());
        assertEquals("Layer 1", l.getName());
        assertTrue(l.isVisible());
        assertNotNull(l.getShapes());
        assertTrue(l.getShapes().isEmpty());
    }

    @Test
    void testSetVisible() {
        Layer l = new Layer(1L, "L");
        l.setVisible(false);
        assertFalse(l.isVisible());
        l.setVisible(true);
        assertTrue(l.isVisible());
    }

    @Test
    void testAddShape() {
        Layer l = new Layer(1L, "L");
        Shape s = new Point();
        l.addShape(s);
        assertEquals(1, l.getShapes().size());
        assertSame(s, l.getShapes().get(0));
    }

    @Test
    void testRemoveShape() {
        Layer l = new Layer(1L, "L");
        Shape s = new Point();
        l.addShape(s);
        assertTrue(l.removeShape(s));
        assertTrue(l.getShapes().isEmpty());
    }

    @Test
    void testRemoveShapeAbsente() {
        Layer l = new Layer(1L, "L");
        Shape s = new Point();
        assertFalse(l.removeShape(s));
    }
}
