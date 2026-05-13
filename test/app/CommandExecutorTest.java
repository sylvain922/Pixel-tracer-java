package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scene.IdGenerator;
import shape.Circle;
import shape.Curve;
import shape.Line;
import shape.Point;
import shape.Polygon;
import shape.Rectangle;
import shape.Shape;
import shape.Square;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class CommandExecutorTest {

    private PixelTracerApp app;
    private CommandExecutor executor;
    private ByteArrayOutputStream out;

    @BeforeEach
    void setup() {
        IdGenerator.reset();
        app = new PixelTracerApp();
        app.init();
        out = new ByteArrayOutputStream();
        executor = new CommandExecutor(app, new PrintStream(out));
    }

    private CommandResult exec(String line) {
        return executor.execute(CommandParser.parse(line));
    }

    @Test
    void testCommandeNulleEstNoOp() {
        assertEquals(CommandResult.NO_OP, executor.execute(null));
    }

    @Test
    void testCommandeInconnue() {
        assertEquals(CommandResult.ERROR, exec("blabla"));
    }

    @Test
    void testExit() {
        assertEquals(CommandResult.EXIT, exec("exit"));
    }

    @Test
    void testClear() {
        assertEquals(CommandResult.CLEAR_SCREEN, exec("clear"));
    }

    @Test
    void testPlot() {
        assertEquals(CommandResult.OK_REDRAW, exec("plot"));
    }

    @Test
    void testHelp() {
        assertEquals(CommandResult.NO_OP, exec("help"));
        assertTrue(out.toString().contains("VECTOR TEXT-BASED EDITOR"));
    }

    @Test
    void testPointAjouteAuLayer() {
        assertEquals(CommandResult.OK_REDRAW, exec("point 3 4"));
        assertEquals(1, app.getCurrentLayer().getShapes().size());
        Shape s = app.getCurrentLayer().getShapes().get(0);
        assertInstanceOf(Point.class, s);
        Point p = (Point) s;
        assertEquals(3, p.getPos_x());
        assertEquals(4, p.getPos_y());
        assertTrue(p.getId() > 0);
    }

    @Test
    void testPointBadParams() {
        assertEquals(CommandResult.ERROR, exec("point 3"));
        assertTrue(app.getCurrentLayer().getShapes().isEmpty());
    }

    @Test
    void testLine() {
        assertEquals(CommandResult.OK_REDRAW, exec("line 1 2 3 4"));
        Line l = (Line) app.getCurrentLayer().getShapes().get(0);
        assertEquals(1, l.getP1().getPos_x());
        assertEquals(4, l.getP2().getPos_y());
    }

    @Test
    void testSquare() {
        exec("square 0 0 5");
        Square s = (Square) app.getCurrentLayer().getShapes().get(0);
        assertEquals(5, s.getLength());
    }

    @Test
    void testRectangle() {
        exec("rectangle 0 0 10 5");
        Rectangle r = (Rectangle) app.getCurrentLayer().getShapes().get(0);
        assertEquals(10, r.getWidth());
        assertEquals(5, r.getHeight());
    }

    @Test
    void testCircle() {
        exec("circle 10 10 5");
        Circle c = (Circle) app.getCurrentLayer().getShapes().get(0);
        assertEquals(5, c.getRadius());
        assertEquals(10, c.getCenter().getPos_x());
    }

    @Test
    void testPolygon() {
        exec("polygon 0 0 5 0 5 5");
        Polygon p = (Polygon) app.getCurrentLayer().getShapes().get(0);
        assertEquals(3, p.getPoints().size());
    }

    @Test
    void testPolygonOddIntsRejected() {
        assertEquals(CommandResult.ERROR, exec("polygon 0 0 5"));
    }

    @Test
    void testPolygonTooShortRejected() {
        assertEquals(CommandResult.ERROR, exec("polygon"));
    }

    @Test
    void testCurve() {
        exec("curve 0 0 1 1 2 2 3 3");
        Curve c = (Curve) app.getCurrentLayer().getShapes().get(0);
        assertEquals(3, c.getP4().getPos_y());
    }

    @Test
    void testNewLayer() {
        int avant = app.getCurrentArea().getLayers().size();
        exec("new layer");
        assertEquals(avant + 1, app.getCurrentArea().getLayers().size());
        assertSame(
            app.getCurrentArea().getLayers().get(app.getCurrentArea().getLayers().size() - 1),
            app.getCurrentLayer());
    }

    @Test
    void testNewArea() {
        int avant = app.getAreas().size();
        exec("new area");
        assertEquals(avant + 1, app.getAreas().size());
    }

    @Test
    void testSelectShape() {
        exec("point 1 2");
        long id = app.getCurrentLayer().getShapes().get(0).getId();
        assertEquals(CommandResult.NO_OP, exec("select shape " + id));
        assertNotNull(app.getCurrentShape());
        assertEquals(id, app.getCurrentShape().getId());
    }

    @Test
    void testSelectShapeIdInconnu() {
        assertEquals(CommandResult.ERROR, exec("select shape 9999"));
    }

    @Test
    void testDeleteShape() {
        exec("point 1 2");
        long id = app.getCurrentLayer().getShapes().get(0).getId();
        assertEquals(CommandResult.OK_REDRAW, exec("delete shape " + id));
        assertTrue(app.getCurrentLayer().getShapes().isEmpty());
    }

    @Test
    void testDeleteResetCurrentShape() {
        exec("point 1 2");
        long id = app.getCurrentLayer().getShapes().get(0).getId();
        exec("select shape " + id);
        exec("delete shape " + id);
        assertNull(app.getCurrentShape());
    }

    @Test
    void testSetCharBackground() {
        assertEquals(CommandResult.OK_REDRAW, exec("set char background 32"));
        assertEquals(' ', app.getCurrentArea().getEmptyChar());
    }

    @Test
    void testSetCharBorder() {
        exec("set char border 35");
        assertEquals('#', app.getCurrentArea().getFullChar());
    }

    @Test
    void testSetLayerInvisible() {
        long id = app.getCurrentLayer().getId();
        exec("set layer unvisible " + id);
        assertFalse(app.getCurrentLayer().isVisible());
        exec("set layer visible " + id);
        assertTrue(app.getCurrentLayer().isVisible());
    }

    @Test
    void testListAreasAfficheLeCurrent() {
        exec("list areas");
        String s = out.toString();
        assertTrue(s.contains("*"));
        assertTrue(s.contains("Area1"));
    }
}
