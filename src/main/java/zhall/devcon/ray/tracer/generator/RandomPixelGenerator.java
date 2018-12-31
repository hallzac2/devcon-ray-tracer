package zhall.devcon.ray.tracer.generator;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 *
 * @author zhall
 */
public class RandomPixelGenerator implements PixelGenerator {

    private final Function<Random, Color> getRandomColor;

    public RandomPixelGenerator() {
        getRandomColor = r -> new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
    }

    public RandomPixelGenerator(Function<Random, Color> getRandomColor) {
        this.getRandomColor = getRandomColor;
    }

    @Override
    public Map<Point, Color> getPixels(int width, int height) {
        Map<Point, Color> pixels = new HashMap<>();
        Random rand = new Random();

        IntStream.range(0, width).forEach(x -> {
            IntStream.range(0, height).forEach(y -> {
                Point p = new Point(x, y);
                Color c = getRandomColor.apply(rand);
                pixels.put(p, c);
            });
        });

        return pixels;
    }
}
