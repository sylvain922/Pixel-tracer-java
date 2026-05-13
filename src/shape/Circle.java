package shape;


public class Circle extends Shape {

    private Point center;
    private int radius;

    public Circle() {
    }

    public void setCenter(Point newVar) {
        center = newVar;
    }

    public Point getCenter() {
        return center;
    }

    public void setRadius(int newVar) {
        radius = newVar;
    }

    public int getRadius() {
        return radius;
    }

    public String toString() {
        return "Circle[center=" + center + ", radius=" + radius + "]";
    }
}
