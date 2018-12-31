package zhall.devcon.ray.tracer.model.scene.shape;

import java.util.Optional;
import zhall.devcon.ray.tracer.model.Intersection;
import zhall.devcon.ray.tracer.util.Vector;

/**
 *
 * @author zhall
 */
public interface SceneShape {
    public Optional<Intersection> determineIntersection(Vector point, Vector direction);
    public Vector getNormalAtPoint(Vector point);
    public Vector getCenter();
}
