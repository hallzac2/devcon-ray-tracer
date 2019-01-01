package zhall.devcon.ray.tracer.util;

import java.util.function.BiFunction;

/**
 *
 * @author zhall
 */
public class Vector {

    private final double x;
    private final double y;
    private final double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static Vector add(Vector v1, Vector v2) {
        return combineVectors((a, b) -> a + b, v1, v2);
    }

    public static Vector subtract(Vector v1, Vector v2) {
        return combineVectors((a, b) -> a - b, v1, v2);
    }

    public static Vector multiply(Vector v1, Vector v2) {
        return combineVectors((a, b) -> a * b, v1, v2);
    }

    public static Vector scale(double scaler, Vector v) {
        return new Vector(v.x * scaler, v.y * scaler, v.z * scaler);
    }

    public static double dot(Vector v1, Vector v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static double length(Vector v) {
        return Math.sqrt(dot(v, v));
    }

    public static Vector normalize(Vector v) {
        double length = length(v);
        return new Vector(v.x / length, v.y / length, v.z / length);
    }

    public static Vector createParametricPoint(Vector point, Vector direction, double t) {
        return add(point, scale(t, direction));
    }

    public static Vector reflect(Vector direction, Vector normal) {
        Vector normalizedNormal = normalize(normal);
        double directionDotNormal = -2 * dot(direction, normalizedNormal);
        Vector scaledNormal = scale(directionDotNormal, normalizedNormal);

        return add(scaledNormal, direction);
    }

    private static Vector combineVectors(BiFunction<Double, Double, Double> combiner, Vector v1, Vector v2) {
        double x = combiner.apply(v1.x, v2.x);
        double y = combiner.apply(v1.y, v2.y);
        double z = combiner.apply(v1.z, v2.z);

        return new Vector(x, y, z);
    }
}
