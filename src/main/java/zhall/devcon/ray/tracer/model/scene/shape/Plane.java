package zhall.devcon.ray.tracer.model.scene.shape;

import java.util.Optional;
import zhall.devcon.ray.tracer.model.Intersection;
import zhall.devcon.ray.tracer.model.LightProperties;
import zhall.devcon.ray.tracer.util.Vector;
import static zhall.devcon.ray.tracer.util.Vector.dot;

/**
 *
 * @author zhall
 */
public class Plane extends SceneObject {

    private final Vector center;
    private final Vector normal;

    public Plane(LightProperties lightProperties, double reflectiveConstant, boolean isLightSource, Vector center, Vector normal) {
        super(lightProperties, reflectiveConstant, isLightSource);
        this.center = center;
        this.normal = normal;
    }

    @Override
    public Optional<Intersection> determineIntersection(Vector point, Vector direction) {
        double directionDotNormal = Vector.dot(direction, normal);
        if (directionDotNormal != 0.0) {
            double centerDotNormal = dot(center, normal);
            double pointDotNormal = dot(point, normal);
            double t = (centerDotNormal - pointDotNormal) / directionDotNormal;

            if (t >= 0.0) {
                return Optional.of(new Intersection(point, direction, t, this));
            }
        }
        return Optional.empty();
    }

    public Intersection determineIntersectionV2(Vector point, Vector direction) {
        double directionDotNormal = Vector.dot(direction, normal);
        if (directionDotNormal != 0.0) {
            double centerDotNormal = dot(center, normal);
            double pointDotNormal = dot(point, normal);
            double t = (centerDotNormal - pointDotNormal) / directionDotNormal;

            if (t >= 0.0) {
                return new Intersection(point, direction, t, this);
            }
        }
        return null;
    }

    @Override
    public Vector getNormalAtPoint(Vector point) {
        return normal;
    }

    @Override
    public Vector getCenter() {
        return center;
    }
}
