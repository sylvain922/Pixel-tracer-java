package app;

import scene.Area;
import scene.IdGenerator;
import scene.Layer;
import shape.Circle;
import shape.Curve;
import shape.Line;
import shape.Point;
import shape.Polygon;
import shape.Rectangle;
import shape.Shape;
import shape.Square;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class CommandExecutor {

    private final PixelTracerApp app;
    private final PrintStream out;

    public CommandExecutor(PixelTracerApp app) {
        this(app, System.out);
    }

    public CommandExecutor(PixelTracerApp app, PrintStream out) {
        this.app = app;
        this.out = out;
    }

    public CommandResult execute(Command cmd) {
        if (cmd == null) {
            return CommandResult.NO_OP;
        }
        return switch (cmd.getName()) {
            case "exit"      -> handleExit(cmd);
            case "clear"     -> handleClear(cmd);
            case "help"      -> handleHelp(cmd);
            case "plot"      -> handlePlot(cmd);
            case "point"     -> handlePoint(cmd);
            case "line"      -> handleLine(cmd);
            case "square"    -> handleSquare(cmd);
            case "rectangle" -> handleRectangle(cmd);
            case "circle"    -> handleCircle(cmd);
            case "polygon"   -> handlePolygon(cmd);
            case "curve"     -> handleCurve(cmd);
            case "list"      -> handleList(cmd);
            case "new"       -> handleNew(cmd);
            case "select"    -> handleSelect(cmd);
            case "delete"    -> handleDelete(cmd);
            case "set"       -> handleSet(cmd);
            default -> {
                out.println("commande inconnue");
                yield CommandResult.ERROR;
            }
        };
    }

    private CommandResult handleExit(Command cmd) {
        if (!cmd.matches(0, 0)) return paramError();
        return CommandResult.EXIT;
    }

    private CommandResult handleClear(Command cmd) {
        if (!cmd.matches(0, 0)) return paramError();
        return CommandResult.CLEAR_SCREEN;
    }

    private CommandResult handlePlot(Command cmd) {
        if (!cmd.matches(0, 0)) return paramError();
        return CommandResult.OK_REDRAW;
    }

    private CommandResult handleHelp(Command cmd) {
        if (!cmd.matches(0, 0)) return paramError();
        printHelp();
        return CommandResult.NO_OP;
    }

    private CommandResult handlePoint(Command cmd) {
        if (!cmd.matches(0, 2)) return paramError();
        Point p = makePoint(cmd.getInts().get(0), cmd.getInts().get(1));
        p.setId(IdGenerator.next());
        app.getCurrentLayer().addShape(p);
        return CommandResult.OK_REDRAW;
    }

    private CommandResult handleLine(Command cmd) {
        if (!cmd.matches(0, 4)) return paramError();
        List<Integer> in = cmd.getInts();
        Line l = new Line();
        l.setP1(makePoint(in.get(0), in.get(1)));
        l.setP2(makePoint(in.get(2), in.get(3)));
        l.setId(IdGenerator.next());
        app.getCurrentLayer().addShape(l);
        return CommandResult.OK_REDRAW;
    }

    private CommandResult handleSquare(Command cmd) {
        if (!cmd.matches(0, 3)) return paramError();
        List<Integer> in = cmd.getInts();
        Square s = new Square();
        s.setP1(makePoint(in.get(0), in.get(1)));
        s.setLength(in.get(2));
        s.setId(IdGenerator.next());
        app.getCurrentLayer().addShape(s);
        return CommandResult.OK_REDRAW;
    }

    private CommandResult handleRectangle(Command cmd) {
        if (!cmd.matches(0, 4)) return paramError();
        List<Integer> in = cmd.getInts();
        Rectangle r = new Rectangle();
        r.setP1(makePoint(in.get(0), in.get(1)));
        r.setWidth(in.get(2));
        r.setHeight(in.get(3));
        r.setId(IdGenerator.next());
        app.getCurrentLayer().addShape(r);
        return CommandResult.OK_REDRAW;
    }

    private CommandResult handleCircle(Command cmd) {
        if (!cmd.matches(0, 3)) return paramError();
        List<Integer> in = cmd.getInts();
        Circle c = new Circle();
        c.setCenter(makePoint(in.get(0), in.get(1)));
        c.setRadius(in.get(2));
        c.setId(IdGenerator.next());
        app.getCurrentLayer().addShape(c);
        return CommandResult.OK_REDRAW;
    }

    private CommandResult handlePolygon(Command cmd) {
        List<Integer> in = cmd.getInts();
        if (!cmd.getStrs().isEmpty() || in.size() < 2 || in.size() % 2 != 0) {
            return paramError();
        }
        Polygon poly = new Polygon();
        ArrayList<Point> pts = new ArrayList<>();
        for (int i = 0; i < in.size(); i += 2) {
            pts.add(makePoint(in.get(i), in.get(i + 1)));
        }
        poly.setPoints(pts);
        poly.setId(IdGenerator.next());
        app.getCurrentLayer().addShape(poly);
        return CommandResult.OK_REDRAW;
    }

    private CommandResult handleCurve(Command cmd) {
        if (!cmd.matches(0, 8)) return paramError();
        List<Integer> in = cmd.getInts();
        Curve c = new Curve();
        c.setP1(makePoint(in.get(0), in.get(1)));
        c.setP2(makePoint(in.get(2), in.get(3)));
        c.setP3(makePoint(in.get(4), in.get(5)));
        c.setP4(makePoint(in.get(6), in.get(7)));
        c.setId(IdGenerator.next());
        app.getCurrentLayer().addShape(c);
        return CommandResult.OK_REDRAW;
    }

    private CommandResult handleList(Command cmd) {
        if (!cmd.matches(1, 0)) return paramError();
        return switch (cmd.getStrs().get(0)) {
            case "areas"  -> { listAreas();  yield CommandResult.NO_OP; }
            case "layers" -> { listLayers(); yield CommandResult.NO_OP; }
            case "shapes" -> { listShapes(); yield CommandResult.NO_OP; }
            default       -> paramError();
        };
    }

    private void listAreas() {
        for (Area a : app.getAreas()) {
            out.printf(" %s %3d %s%n",
                a == app.getCurrentArea() ? "*" : "-",
                a.getId(), a.getName());
        }
    }

    private void listLayers() {
        for (Layer l : app.getCurrentArea().getLayers()) {
            out.printf(" %s %3d (%c) %s%n",
                l == app.getCurrentLayer() ? "*" : "-",
                l.getId(),
                l.isVisible() ? 'V' : 'H',
                l.getName());
        }
    }

    private void listShapes() {
        for (Shape s : app.getCurrentLayer().getShapes()) {
            out.printf(" %s %3d : %s%n",
                s == app.getCurrentShape() ? "*" : "-",
                s.getId(),
                s.toString());
        }
    }

    private CommandResult handleNew(Command cmd) {
        if (!cmd.matches(1, 0)) return paramError();
        return switch (cmd.getStrs().get(0)) {
            case "area"  -> { newArea();  yield CommandResult.OK_REDRAW; }
            case "layer" -> { newLayer(); yield CommandResult.OK_REDRAW; }
            default      -> paramError();
        };
    }

    private void newArea() {
        Area area = new Area(PixelTracerApp.DEFAULT_WIDTH,
                             PixelTracerApp.DEFAULT_HEIGHT,
                             IdGenerator.next(), "area_name");
        Layer layer = new Layer(IdGenerator.next(), "Layer 1");
        area.addLayer(layer);
        app.getAreas().add(area);
        app.setCurrentArea(area);
        app.setCurrentLayer(layer);
        app.setCurrentShape(null);
    }

    private void newLayer() {
        Layer layer = new Layer(IdGenerator.next(), "layer_name");
        app.getCurrentArea().addLayer(layer);
        app.setCurrentLayer(layer);
        app.setCurrentShape(null);
    }

    private CommandResult handleSelect(Command cmd) {
        if (!cmd.matches(1, 1)) return paramError();
        long id = cmd.getInts().get(0);
        return switch (cmd.getStrs().get(0)) {
            case "area"  -> selectArea(id);
            case "layer" -> selectLayer(id);
            case "shape" -> selectShape(id);
            default      -> paramError();
        };
    }

    private CommandResult selectArea(long id) {
        for (Area a : app.getAreas()) {
            if (a.getId() == id) {
                app.setCurrentArea(a);
                if (!a.getLayers().isEmpty()) {
                    app.setCurrentLayer(a.getLayers().get(a.getLayers().size() - 1));
                }
                app.setCurrentShape(null);
                return CommandResult.NO_OP;
            }
        }
        return unknownId();
    }

    private CommandResult selectLayer(long id) {
        for (Layer l : app.getCurrentArea().getLayers()) {
            if (l.getId() == id) {
                app.setCurrentLayer(l);
                app.setCurrentShape(null);
                return CommandResult.NO_OP;
            }
        }
        return unknownId();
    }

    private CommandResult selectShape(long id) {
        for (Shape s : app.getCurrentLayer().getShapes()) {
            if (s.getId() == id) {
                app.setCurrentShape(s);
                return CommandResult.NO_OP;
            }
        }
        return unknownId();
    }

    private CommandResult handleDelete(Command cmd) {
        if (!cmd.matches(1, 1)) return paramError();
        if (!"shape".equals(cmd.getStrs().get(0))) return paramError();
        long id = cmd.getInts().get(0);
        List<Shape> shapes = app.getCurrentLayer().getShapes();
        for (int i = 0; i < shapes.size(); i++) {
            Shape s = shapes.get(i);
            if (s.getId() == id) {
                shapes.remove(i);
                if (app.getCurrentShape() == s) {
                    app.setCurrentShape(null);
                }
                return CommandResult.OK_REDRAW;
            }
        }
        return unknownId();
    }

    private CommandResult handleSet(Command cmd) {
        if (!cmd.matches(2, 1)) return paramError();
        String what = cmd.getStrs().get(0);
        String which = cmd.getStrs().get(1);
        int value = cmd.getInts().get(0);
        if ("char".equals(what)) {
            return setChar(which, value);
        }
        if ("layer".equals(what)) {
            return setLayer(which, value);
        }
        return paramError();
    }

    private CommandResult setChar(String which, int code) {
        char c = (char) code;
        return switch (which) {
            case "border"     -> { app.getCurrentArea().setFullChar(c);  yield CommandResult.OK_REDRAW; }
            case "background" -> { app.getCurrentArea().setEmptyChar(c); yield CommandResult.OK_REDRAW; }
            default           -> paramError();
        };
    }

    private CommandResult setLayer(String which, long id) {
        boolean visible;
        if ("visible".equals(which)) {
            visible = true;
        } else if ("unvisible".equals(which)) {
            visible = false;
        } else {
            return paramError();
        }
        for (Layer l : app.getCurrentArea().getLayers()) {
            if (l.getId() == id) {
                l.setVisible(visible);
                return CommandResult.OK_REDRAW;
            }
        }
        return unknownId();
    }

    private Point makePoint(int x, int y) {
        Point p = new Point();
        p.setPos_x(x);
        p.setPos_y(y);
        return p;
    }

    private CommandResult paramError() {
        out.println("erreur paramètres, consulter la commande help");
        return CommandResult.ERROR;
    }

    private CommandResult unknownId() {
        out.println("id inconnu dans la liste");
        return CommandResult.ERROR;
    }

    private void printHelp() {
        out.println("\t**************************************************");
        out.println("\t****         VECTOR TEXT-BASED EDITOR         ****");
        out.println("\t**************************************************");
        out.println("\t==== Control ====");
        out.println("\tclear : clear screen");
        out.println("\texit  : exit the program");
        out.println("\thelp  : print this help");
        out.println("\tplot  : redraw screen");
        out.println("\t==== Draw shapes ====");
        out.println("\tpoint x y                          : draw a point at (x, y)");
        out.println("\tline x1 y1 x2 y2                   : draw a line");
        out.println("\tsquare x y length                  : draw a square");
        out.println("\trectangle x y width height         : draw a rectangle");
        out.println("\tcircle x y radius                  : draw a circle");
        out.println("\tpolygon x1 y1 x2 y2 ...            : draw a polygon");
        out.println("\tcurve x1 y1 x2 y2 x3 y3 x4 y4      : draw a Bezier curve");
        out.println("\t==== Draw manager ====");
        out.println("\tlist {areas | layers | shapes}");
        out.println("\tselect {area | layer | shape} id");
        out.println("\tdelete shape id");
        out.println("\tnew {area | layer}");
        out.println("\t==== Set ====");
        out.println("\tset char {border | background} ascii_code");
        out.println("\tset layer {visible | unvisible} id");
    }
}
