package zhall.devcon.ray.tracer.generator;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

/**
 *
 * @author zhall
 */
public interface PixelGenerator {

    Map<Point, Color> getPixels(int width, int height);
}
