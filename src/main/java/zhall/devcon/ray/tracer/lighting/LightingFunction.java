package zhall.devcon.ray.tracer.lighting;

import java.util.Collection;
import zhall.devcon.ray.tracer.model.Intersection;
import zhall.devcon.ray.tracer.model.scene.shape.SceneObject;
import zhall.devcon.ray.tracer.util.Vector;

/**
 *
 * @author zhall
 */
public interface LightingFunction {

    public Vector calculate(Collection<SceneObject> objectsInScene, Vector eye, Intersection intersection);
}
