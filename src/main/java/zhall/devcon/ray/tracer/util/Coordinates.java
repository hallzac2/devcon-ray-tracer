package zhall.devcon.ray.tracer.util;

/**
 *
 * @author zhall
 */
public class Coordinates {

    private Coordinates() {
    }

    public static double normalize(double value, double dimension) {
        return (value / dimension) * 2 - 1;
    }
}
