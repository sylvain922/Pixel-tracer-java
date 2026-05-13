package shape;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void testSetEtGetP1() {
        Square s = new Square();
        Point p = new Point();
        p.setPos_x(2);
        p.setPos_y(3);
        s.setP1(p);
        assertEquals(2, s.getP1().getPos_x());
        assertEquals(3, s.getP1().getPos_y());
    }

    @Test
    void testP1ParDefautEstNull() {
        Square s = new Square();
        assertNull(s.getP1());
    }

    @Test
    void testSetEtGetLength() {
        Square s = new Square();
        s.setLength(15);
        assertEquals(15, s.getLength());
    }

    @Test
    void testLengthParDefautEstZero() {
        Square s = new Square();
        assertEquals(0, s.getLength());
    }

    @Test
    void testToString() {
        Square s = new Square();
        Point p = new Point();
        p.setPos_x(1);
        p.setPos_y(2);
        s.setP1(p);
        s.setLength(8);
        assertEquals("Square[p1=Point[pos_x=1, pos_y=2], length=8]", s.toString());
    }
}
