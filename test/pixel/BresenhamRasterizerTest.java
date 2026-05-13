package pixel;

import org.junit.jupiter.api.Test;
import shape.Circle;
import shape.Curve;
import shape.Line;
import shape.Point;
import shape.Polygon;
import shape.Rectangle;
import shape.Square;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BresenhamRasterizerTest {

    private final Rasterizer rasterizer = new BresenhamRasterizer();

    private Point pt(int x, int y) {
        Point p = new Point();
        p.setPos_x(x);
        p.setPos_y(y);
        return p;
    }

    private boolean contains(List<Pixel> pixels, int x, int y) {
        return pixels.stream().anyMatch(p -> p.x() == x && p.y() == y);
    }

    @Test
    void testPoint() {
        Point p = pt(3, 4);
        p.setColor(0xFF0000);
        List<Pixel> pixels = rasterizer.rasterize(p);
        assertEquals(1, pixels.size());
        assertEquals(new Pixel(3, 4, 0xFF0000), pixels.get(0));
    }

    @Test
    void testHorizontalLine() {
        Line l = new Line();
        l.setP1(pt(0, 0));
        l.setP2(pt(3, 0));
        List<Pixel> pixels = rasterizer.rasterize(l);
        assertEquals(4, pixels.size());
        assertTrue(contains(pixels, 0, 0));
        assertTrue(contains(pixels, 1, 0));
        assertTrue(contains(pixels, 2, 0));
        assertTrue(contains(pixels, 3, 0));
    }

    @Test
    void testVerticalLine() {
        Line l = new Line();
        l.setP1(pt(0, 0));
        l.setP2(pt(0, 3));
        List<Pixel> pixels = rasterizer.rasterize(l);
        assertEquals(4, pixels.size());
        assertTrue(contains(pixels, 0, 0));
        assertTrue(contains(pixels, 0, 1));
        assertTrue(contains(pixels, 0, 2));
        assertTrue(contains(pixels, 0, 3));
    }

    @Test
    void testDiagonalLine() {
        Line l = new Line();
        l.setP1(pt(0, 0));
        l.setP2(pt(3, 3));
        List<Pixel> pixels = rasterizer.rasterize(l);
        assertEquals(4, pixels.size());
        assertTrue(contains(pixels, 0, 0));
        assertTrue(contains(pixels, 1, 1));
        assertTrue(contains(pixels, 2, 2));
        assertTrue(contains(pixels, 3, 3));
    }

    @Test
    void testReverseLine() {
        Line l = new Line();
        l.setP1(pt(3, 3));
        l.setP2(pt(0, 0));
        List<Pixel> pixels = rasterizer.rasterize(l);
        assertEquals(4, pixels.size());
        assertTrue(contains(pixels, 3, 3));
        assertTrue(contains(pixels, 0, 0));
    }

    @Test
    void testSquareHasFourCorners() {
        Square s = new Square();
        s.setP1(pt(0, 0));
        s.setLength(4);
        List<Pixel> pixels = rasterizer.rasterize(s);
        assertTrue(contains(pixels, 0, 0));
        assertTrue(contains(pixels, 3, 0));
        assertTrue(contains(pixels, 0, 3));
        assertTrue(contains(pixels, 3, 3));
    }

    @Test
    void testRectangleHasFourCorners() {
        Rectangle r = new Rectangle();
        r.setP1(pt(0, 0));
        r.setWidth(5);
        r.setHeight(3);
        List<Pixel> pixels = rasterizer.rasterize(r);
        assertTrue(contains(pixels, 0, 0));
        assertTrue(contains(pixels, 4, 0));
        assertTrue(contains(pixels, 0, 2));
        assertTrue(contains(pixels, 4, 2));
    }

    @Test
    void testCircleContainsCardinalPoints() {
        Circle c = new Circle();
        c.setCenter(pt(10, 10));
        c.setRadius(5);
        List<Pixel> pixels = rasterizer.rasterize(c);
        assertTrue(contains(pixels, 10, 15));
        assertTrue(contains(pixels, 10, 5));
        assertTrue(contains(pixels, 15, 10));
        assertTrue(contains(pixels, 5, 10));
    }

    @Test
    void testPolygonChainsSegments() {
        Polygon poly = new Polygon();
        ArrayList<Point> pts = new ArrayList<>();
        pts.add(pt(0, 0));
        pts.add(pt(3, 0));
        pts.add(pt(3, 3));
        poly.setPoints(pts);
        List<Pixel> pixels = rasterizer.rasterize(poly);
        assertTrue(contains(pixels, 0, 0));
        assertTrue(contains(pixels, 3, 0));
        assertTrue(contains(pixels, 3, 3));
    }

    @Test
    void testCurveStartsAndEndsNearControlPoints() {
        Curve c = new Curve();
        c.setP1(pt(0, 0));
        c.setP2(pt(10, 0));
        c.setP3(pt(10, 10));
        c.setP4(pt(20, 10));
        List<Pixel> pixels = rasterizer.rasterize(c);
        assertFalse(pixels.isEmpty());
        // t=0 -> p1 exactly
        assertEquals(0, pixels.get(0).x());
        assertEquals(0, pixels.get(0).y());
        // dernier pixel : t proche de 1 -> proche de p4
        Pixel last = pixels.get(pixels.size() - 1);
        assertTrue(Math.abs(last.x() - 20) <= 1);
        assertTrue(Math.abs(last.y() - 10) <= 1);
    }

    @Test
    void testColorIsCarriedFromShape() {
        Line l = new Line();
        l.setP1(pt(0, 0));
        l.setP2(pt(2, 0));
        l.setColor(0x00FF00);
        List<Pixel> pixels = rasterizer.rasterize(l);
        for (Pixel p : pixels) {
            assertEquals(0x00FF00, p.color());
        }
    }
}
