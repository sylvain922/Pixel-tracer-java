package render;

import pixel.Pixel;
import pixel.Rasterizer;
import scene.Area;
import scene.Layer;
import shape.Shape;


public class ConsoleRenderer {

    private final Rasterizer rasterizer;

    public ConsoleRenderer(Rasterizer rasterizer) {
        this.rasterizer = rasterizer;
    }

    public void drawAllLayers(Area area) {
        area.clear();
        for (Layer layer : area.getLayers()) {
            if (layer.isVisible()) {
                drawLayer(area, layer);
            }
        }
    }

    private void drawLayer(Area area, Layer layer) {
        char[][] grid = area.getGrid();
        char full = area.getFullChar();
        int w = area.getWidth();
        int h = area.getHeight();

        for (Shape shape : layer.getShapes()) {
            for (Pixel p : rasterizer.rasterize(shape)) {
                int x = p.x();
                int y = p.y();
                if (x >= 0 && x < w && y >= 0 && y < h) {
                    grid[y][x] = full;
                }
            }
        }
    }

    public String render(Area area) {
        char[][] grid = area.getGrid();
        int w = area.getWidth();
        int h = area.getHeight();
        StringBuilder sb = new StringBuilder((w + 1) * h);
        for (int y = 0; y < h; y++) {
            sb.append(grid[y], 0, w);
            sb.append('\n');
        }
        return sb.toString();
    }

    public void print(Area area) {
        System.out.print(render(area));
    }

    public void clearScreen() {
        System.out.print("\033[H\033[J");
        System.out.flush();
    }
}
