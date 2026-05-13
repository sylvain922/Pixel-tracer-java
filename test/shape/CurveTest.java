package shape;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurveTest {

    private Point pt(int x, int y) {
        Point p = new Point();
        p.setPos_x(x);
        p.setPos_y(y);
        return p;
    }

    @Test
    void testSetEtGetPoints() {
        Curve c = new Curve();
        c.setP1(pt(0, 0));
        c.setP2(pt(1, 2));
        c.setP3(pt(3, 4));
        c.setP4(pt(5, 6));
        assertEquals(0, c.getP1().getPos_x());
        assertEquals(2, c.getP2().getPos_y());
        assertEquals(3, c.getP3().getPos_x());
        assertEquals(6, c.getP4().getPos_y());
    }

    @Test
    void testPointsParDefautEstNull() {
        Curve c = new Curve();
        assertNull(c.getP1());
        assertNull(c.getP2());
        assertNull(c.getP3());
        assertNull(c.getP4());
    }

    @Test
    void testToString() {
        Curve c = new Curve();
        c.setP1(pt(0, 0));
        c.setP2(pt(1, 1));
        c.setP3(pt(2, 2));
        c.setP4(pt(3, 3));
        assertEquals(
            "Curve[p1=Point[pos_x=0, pos_y=0], p2=Point[pos_x=1, pos_y=1], "
                + "p3=Point[pos_x=2, pos_y=2], p4=Point[pos_x=3, pos_y=3]]",
            c.toString());
    }
}
