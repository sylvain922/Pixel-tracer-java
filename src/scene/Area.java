package scene;

import java.util.ArrayList;
import java.util.List;


public class Area {

    private long id;
    private String name;
    private int width;
    private int height;
    private char[][] grid;
    private List<Layer> layers;
    private char emptyChar;
    private char fullChar;

    public Area(int width, int height, long id, String name) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.emptyChar = '.';
        this.fullChar = '@';
        this.layers = new ArrayList<>();
        this.grid = new char[height][width];
        clear();
    }

    public void clear() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = emptyChar;
            }
        }
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getGrid() {
        return grid;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public boolean removeLayer(Layer layer) {
        return layers.remove(layer);
    }

    public char getEmptyChar() {
        return emptyChar;
    }

    public void setEmptyChar(char emptyChar) {
        this.emptyChar = emptyChar;
    }

    public char getFullChar() {
        return fullChar;
    }

    public void setFullChar(char fullChar) {
        this.fullChar = fullChar;
    }

    public String toString() {
        return "Area[id=" + id + ", name=" + name
                + ", " + width + "x" + height
                + ", layers=" + layers.size() + "]";
    }
}
