package zhall.devcon.ray.tracer.model.scene.shape;

import java.util.Optional;
import zhall.devcon.ray.tracer.model.Intersection;
import zhall.devcon.ray.tracer.model.LightProperties;
import zhall.devcon.ray.tracer.util.Vector;
import static zhall.devcon.ray.tracer.util.Vector.dot;
import static zhall.devcon.ray.tracer.util.Vector.normalize;
import static zhall.devcon.ray.tracer.util.Vector.subtract;

/**
 *
 * @author zhall
 */
public class Sphere extends SceneObject {

    private final Vector center;
    private final double radius;

    public Sphere(LightProperties lightProperties, double reflectiveConstant, boolean isLightSource, Vector center, double radius) {
        super(lightProperties, reflectiveConstant, isLightSource);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Optional<Intersection> determineIntersection(Vector point, Vector direction) {
        Vector pointLessCenter = subtract(point, center);
        double a = dot(direction, direction);
        double b = 2 * dot(pointLessCenter, direction);
        double c = dot(pointLessCenter, pointLessCenter) - (radius * radius);
        double discriminant = (b * b) - 4 * a * c;

        if (discriminant >= 0.0) {
            double t;
            if (discriminant == 0.0) // Only one intersection
            {
                t = (-b) / (2 * a);
            } else // Two intersections, take the smaller t value
            {
                double t1 = ((-b) + Math.sqrt(discriminant)) / (2 * a);
                double t2 = ((-b) - Math.sqrt(discriminant)) / (2 * a);
                t = (t1 < t2) ? t1 : t2;
            }
            if (t >= 0.0) {
                return Optional.of(new Intersection(point, direction, t, this));
            }
        }
        return Optional.empty();
    }

    @Override
    public Vector getNormalAtPoint(Vector point) {
        return normalize(subtract(point, center));
    }

    @Override
    public Vector getCenter() {
        return center;
    }
}
