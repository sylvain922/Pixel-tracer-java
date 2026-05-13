package shape;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void testSetEtGetP1() {
        Rectangle r = new Rectangle();
        Point p = new Point();
        p.setPos_x(4);
        p.setPos_y(5);
        r.setP1(p);
        assertEquals(4, r.getP1().getPos_x());
        assertEquals(5, r.getP1().getPos_y());
    }

    @Test
    void testP1ParDefautEstNull() {
        Rectangle r = new Rectangle();
        assertNull(r.getP1());
    }

    @Test
    void testSetEtGetWidth() {
        Rectangle r = new Rectangle();
        r.setWidth(20);
        assertEquals(20, r.getWidth());
    }

    @Test
    void testSetEtGetHeight() {
        Rectangle r = new Rectangle();
        r.setHeight(10);
        assertEquals(10, r.getHeight());
    }

    @Test
    void testDimensionsParDefautEstZero() {
        Rectangle r = new Rectangle();
        assertEquals(0, r.getWidth());
        assertEquals(0, r.getHeight());
    }

    @Test
    void testToString() {
        Rectangle r = new Rectangle();
        Point p = new Point();
        p.setPos_x(0);
        p.setPos_y(0);
        r.setP1(p);
        r.setWidth(12);
        r.setHeight(6);
        assertEquals("Rectangle[p1=Point[pos_x=0, pos_y=0], width=12, height=6]", r.toString());
    }
}
