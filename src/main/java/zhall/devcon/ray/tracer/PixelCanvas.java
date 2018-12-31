package zhall.devcon.ray.tracer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

/**
 *
 * @author zhall
 */
public class PixelCanvas extends Canvas {

    private final Map<Point, Color> pixels;

    public PixelCanvas(Map<Point, Color> pixels) {
        this.pixels = pixels;
    }

    @Override
    public void paint(Graphics g) {
        pixels.forEach((point, color) -> {
            g.setColor(color);
            g.fillRect(point.x, point.y, 1, 1);
        });
    }
}
