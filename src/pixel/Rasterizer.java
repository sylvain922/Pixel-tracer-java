package pixel;

import shape.Shape;
import java.util.List;


public interface Rasterizer {

    List<Pixel> rasterize(Shape shape);
}
