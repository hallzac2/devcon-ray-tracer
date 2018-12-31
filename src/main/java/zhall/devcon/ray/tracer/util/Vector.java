package zhall.devcon.ray.tracer.util;

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
        double x = v1.x + v2.x;
        double y = v1.y + v2.y;
        double z = v1.z + v2.z;

        return new Vector(x, y, z);
    }

    public static Vector subtract(Vector v1, Vector v2) {
        double x = v1.x - v2.x;
        double y = v1.y - v2.y;
        double z = v1.z - v2.z;

        return new Vector(x, y, z);
    }

    public static Vector multiply(Vector v1, Vector v2) {
        double x = v1.x * v2.x;
        double y = v1.y * v2.y;
        double z = v1.z * v2.z;

        return new Vector(x, y, z);
    }

    public static Vector scale(double scaler, Vector v) {
        double x = v.x * scaler;
        double y = v.y * scaler;
        double z = v.z * scaler;

        return new Vector(x, y, z);
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
}
