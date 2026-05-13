package render;

import org.junit.jupiter.api.Test;
import pixel.BresenhamRasterizer;
import scene.Area;
import scene.Layer;
import shape.Point;
import shape.Line;
import shape.Rectangle;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleRendererTest {

    private final ConsoleRenderer renderer = new ConsoleRenderer(new BresenhamRasterizer());

    private Point pt(int x, int y) {
        Point p = new Point();
        p.setPos_x(x);
        p.setPos_y(y);
        return p;
    }

    @Test
    void testRenderAreaVide() {
        Area a = new Area(3, 2, 1L, "A");
        assertEquals("...\n...\n", renderer.render(a));
    }

    @Test
    void testRenderUtiliseEmptyChar() {
        Area a = new Area(2, 1, 1L, "A");
        a.setEmptyChar(' ');
        a.clear();
        assertEquals("  \n", renderer.render(a));
    }

    @Test
    void testDrawAllLayersEcritUnPoint() {
        Area a = new Area(3, 3, 1L, "A");
        Layer l = new Layer(1L, "L");
        l.addShape(pt(1, 0));
        a.addLayer(l);
        renderer.drawAllLayers(a);
        assertEquals(".@.\n...\n...\n", renderer.render(a));
    }

    @Test
    void testLayerInvisibleNonDessine() {
        Area a = new Area(3, 1, 1L, "A");
        Layer l = new Layer(1L, "L");
        l.addShape(pt(1, 0));
        l.setVisible(false);
        a.addLayer(l);
        renderer.drawAllLayers(a);
        assertEquals("...\n", renderer.render(a));
    }

    @Test
    void testPixelsHorsBornesIgnores() {
        Area a = new Area(3, 3, 1L, "A");
        Layer l = new Layer(1L, "L");
        l.addShape(pt(10, 10));
        l.addShape(pt(-1, 0));
        l.addShape(pt(0, -1));
        l.addShape(pt(1, 1));
        a.addLayer(l);
        renderer.drawAllLayers(a);
        assertEquals("...\n.@.\n...\n", renderer.render(a));
    }

    @Test
    void testLigneHorizontaleAffichee() {
        Area a = new Area(5, 2, 1L, "A");
        Layer l = new Layer(1L, "L");
        Line line = new Line();
        line.setP1(pt(0, 0));
        line.setP2(pt(4, 0));
        l.addShape(line);
        a.addLayer(l);
        renderer.drawAllLayers(a);
        assertEquals("@@@@@\n.....\n", renderer.render(a));
    }

    @Test
    void testRectangleDessineSesBords() {
        Area a = new Area(5, 4, 1L, "A");
        Layer l = new Layer(1L, "L");
        Rectangle r = new Rectangle();
        r.setP1(pt(0, 0));
        r.setWidth(5);
        r.setHeight(4);
        l.addShape(r);
        a.addLayer(l);
        renderer.drawAllLayers(a);
        assertEquals(
            "@@@@@\n"
          + "@...@\n"
          + "@...@\n"
          + "@@@@@\n",
            renderer.render(a));
    }

    @Test
    void testFullCharRespecte() {
        Area a = new Area(2, 1, 1L, "A");
        a.setFullChar('#');
        Layer l = new Layer(1L, "L");
        l.addShape(pt(0, 0));
        a.addLayer(l);
        renderer.drawAllLayers(a);
        assertEquals("#.\n", renderer.render(a));
    }

    @Test
    void testRedrawApresModification() {
        Area a = new Area(3, 1, 1L, "A");
        Layer l = new Layer(1L, "L");
        l.addShape(pt(0, 0));
        a.addLayer(l);
        renderer.drawAllLayers(a);
        assertEquals("@..\n", renderer.render(a));
        l.getShapes().clear();
        l.addShape(pt(2, 0));
        renderer.drawAllLayers(a);
        assertEquals("..@\n", renderer.render(a));
    }
}
