package scene;

import shape.Shape;
import java.util.ArrayList;
import java.util.List;


public class Layer {

    private long id;
    private String name;
    private boolean visible;
    private List<Shape> shapes;

    public Layer(long id, String name) {
        this.id = id;
        this.name = name;
        this.visible = true;
        this.shapes = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public boolean removeShape(Shape shape) {
        return shapes.remove(shape);
    }

    public String toString() {
        return "Layer[id=" + id + ", name=" + name
                + ", visible=" + visible + ", shapes=" + shapes.size() + "]";
    }
}
