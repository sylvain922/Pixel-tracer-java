package shape;

import java.util.ArrayList;


public class Polygon extends Shape {

    private ArrayList<Point> points;

    public Polygon() {
    }

    public void setPoints(ArrayList<Point> newVar) {
        points = newVar;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public String toString() {
        return "Polygon[points=" + points + "]";
    }
}
