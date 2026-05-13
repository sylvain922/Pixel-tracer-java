package shape;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class PolygonTest {

    @Test
    void testSetEtGetPoints() {
        Polygon poly = new Polygon();
        ArrayList<Point> points = new ArrayList<>();
        Point p = new Point();
        p.setPos_x(1);
        p.setPos_y(2);
        points.add(p);
        poly.setPoints(points);
        assertEquals(1, poly.getPoints().size());
        assertEquals(1, poly.getPoints().get(0).getPos_x());
    }

    @Test
    void testPointsParDefautEstNull() {
        Polygon poly = new Polygon();
        assertNull(poly.getPoints());
    }

    @Test
    void testToString() {
        Polygon poly = new Polygon();
        ArrayList<Point> points = new ArrayList<>();
        Point p = new Point();
        p.setPos_x(0);
        p.setPos_y(1);
        points.add(p);
        poly.setPoints(points);
        assertEquals("Polygon[points=[Point[pos_x=0, pos_y=1]]]", poly.toString());
    }
}
