package app;

import scene.Area;
import scene.IdGenerator;
import scene.Layer;
import shape.Shape;
import java.util.ArrayList;
import java.util.List;


public class PixelTracerApp {

    public static final int DEFAULT_WIDTH = 80;
    public static final int DEFAULT_HEIGHT = 40;

    private final List<Area> areas = new ArrayList<>();
    private Area currentArea;
    private Layer currentLayer;
    private Shape currentShape;

    public void init() {
        Area area = new Area(DEFAULT_WIDTH, DEFAULT_HEIGHT,
                             IdGenerator.next(), "Area1");
        Layer layer = new Layer(IdGenerator.next(), "Layer 1");
        area.addLayer(layer);
        areas.add(area);
        currentArea = area;
        currentLayer = layer;
        currentShape = null;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public Area getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(Area area) {
        this.currentArea = area;
    }

    public Layer getCurrentLayer() {
        return currentLayer;
    }

    public void setCurrentLayer(Layer layer) {
        this.currentLayer = layer;
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    public void setCurrentShape(Shape shape) {
        this.currentShape = shape;
    }
}
