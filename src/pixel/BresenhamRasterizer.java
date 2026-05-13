package pixel;

import shape.Circle;
import shape.Curve;
import shape.Line;
import shape.Point;
import shape.Polygon;
import shape.Rectangle;
import shape.Shape;
import shape.Square;
import java.util.ArrayList;
import java.util.List;


public class BresenhamRasterizer implements Rasterizer {

    @Override
    public List<Pixel> rasterize(Shape shape) {
        List<Pixel> pixels = new ArrayList<>();

        if (shape instanceof Point) {
            rasterizePoint((Point) shape, pixels);
        } else if (shape instanceof Line) {
            rasterizeLine((Line) shape, pixels);
        } else if (shape instanceof Circle) {
            rasterizeCircle((Circle) shape, pixels);
        } else if (shape instanceof Rectangle) {
            rasterizeRectangle((Rectangle) shape, pixels);
        } else if (shape instanceof Square) {
            rasterizeSquare((Square) shape, pixels);
        } else if (shape instanceof Polygon) {
            rasterizePolygon((Polygon) shape, pixels);
        } else if (shape instanceof Curve) {
            rasterizeCurve((Curve) shape, pixels);
        }

        return pixels;
    }

    private void rasterizePoint(Point point, List<Pixel> pixels) {
        pixels.add(new Pixel(point.getPos_x(), point.getPos_y(), point.getColor()));
    }

    private void drawSegment(int x, int y, int dx, int dy, int color, List<Pixel> pixels) {
        int cumul;
        int xinc = (dx > 0) ? 1 : -1;
        int yinc = (dy > 0) ? 1 : -1;
        dx = Math.abs(dx);
        dy = Math.abs(dy);

        pixels.add(new Pixel(x, y, color));

        if (dx > dy) {
            cumul = dx / 2;
            for (int i = 1; i <= dx; i++) {
                x += xinc;
                cumul += dy;
                if (cumul >= dx) {
                    cumul -= dx;
                    y += yinc;
                }
                pixels.add(new Pixel(x, y, color));
            }
        } else {
            cumul = dy / 2;
            for (int i = 1; i <= dy; i++) {
                y += yinc;
                cumul += dx;
                if (cumul >= dy) {
                    cumul -= dy;
                    x += xinc;
                }
                pixels.add(new Pixel(x, y, color));
            }
        }
    }

    private void rasterizeLine(Line line, List<Pixel> pixels) {
        int x = line.getP1().getPos_x();
        int y = line.getP1().getPos_y();
        int dx = line.getP2().getPos_x() - line.getP1().getPos_x();
        int dy = line.getP2().getPos_y() - line.getP1().getPos_y();
        drawSegment(x, y, dx, dy, line.getColor(), pixels);
    }

    private void rasterizeCircle(Circle circle, List<Pixel> pixels) {
        int x = 0;
        int y = circle.getRadius();
        int d = circle.getRadius() - 1;
        int cx = circle.getCenter().getPos_x();
        int cy = circle.getCenter().getPos_y();
        int color = circle.getColor();

        while (y >= x) {
            pixels.add(new Pixel(cx + x, cy + y, color));
            pixels.add(new Pixel(cx + y, cy + x, color));
            pixels.add(new Pixel(cx - x, cy + y, color));
            pixels.add(new Pixel(cx - y, cy + x, color));
            pixels.add(new Pixel(cx + x, cy - y, color));
            pixels.add(new Pixel(cx + y, cy - x, color));
            pixels.add(new Pixel(cx - x, cy - y, color));
            pixels.add(new Pixel(cx - y, cy - x, color));

            if (d >= 2 * x) {
                d -= 2 * x + 1;
                x++;
            } else if (d < 2 * (circle.getRadius() - y)) {
                d += 2 * y - 1;
                y--;
            } else {
                d += 2 * (y - x - 1);
                y--;
                x++;
            }
        }
    }

    private void rasterizeRectangle(Rectangle rect, List<Pixel> pixels) {
        int x = rect.getP1().getPos_x();
        int y = rect.getP1().getPos_y();
        int w = rect.getWidth();
        int h = rect.getHeight();
        int color = rect.getColor();

        drawSegment(x, y, 0, w - 1, color, pixels);
        drawSegment(x, y, h - 1, 0, color, pixels);
        drawSegment(x, y + w - 1, h - 1, 0, color, pixels);
        drawSegment(x + h - 1, y, 0, w - 1, color, pixels);
    }

    private void rasterizeSquare(Square square, List<Pixel> pixels) {
        int x = square.getP1().getPos_x();
        int y = square.getP1().getPos_y();
        int l = square.getLength();
        int color = square.getColor();

        drawSegment(x, y, l - 1, 0, color, pixels);
        drawSegment(x, y, 0, l - 1, color, pixels);
        drawSegment(x, y + l - 1, l - 1, 0, color, pixels);
        drawSegment(x + l - 1, y, 0, l - 1, color, pixels);
    }

    private void rasterizePolygon(Polygon polygon, List<Pixel> pixels) {
        List<Point> points = polygon.getPoints();
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            int dx = p2.getPos_x() - p1.getPos_x();
            int dy = p2.getPos_y() - p1.getPos_y();
            drawSegment(p1.getPos_x(), p1.getPos_y(), dx, dy, polygon.getColor(), pixels);
        }
    }

    // De Casteljau algorithm for Bezier curves
    private double[] casteljau(double[][] pts, double t) {
        double[][] tmp = new double[pts.length][2];
        for (int i = 0; i < pts.length; i++) {
            tmp[i][0] = pts[i][0];
            tmp[i][1] = pts[i][1];
        }
        for (int i = tmp.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                tmp[j][0] = tmp[j][0] * (1 - t) + tmp[j + 1][0] * t;
                tmp[j][1] = tmp[j][1] * (1 - t) + tmp[j + 1][1] * t;
            }
        }
        return tmp[0];
    }

    private void rasterizeCurve(Curve curve, List<Pixel> pixels) {
        double[][] points = {
            { curve.getP1().getPos_x(), curve.getP1().getPos_y() },
            { curve.getP2().getPos_x(), curve.getP2().getPos_y() },
            { curve.getP3().getPos_x(), curve.getP3().getPos_y() },
            { curve.getP4().getPos_x(), curve.getP4().getPos_y() }
        };
        int color = curve.getColor();

        for (double t = 0; t < 1.0; t += 0.0001) {
            double[] pt = casteljau(points, t);
            pixels.add(new Pixel((int) pt[0], (int) pt[1], color));
        }
    }
}
