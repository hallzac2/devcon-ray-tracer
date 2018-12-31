package zhall.devcon.ray.tracer.model.scene;

import zhall.devcon.ray.tracer.model.scene.shape.SceneObject;
import java.util.Collection;
import zhall.devcon.ray.tracer.util.Vector;

/**
 *
 * @author zhall
 */
public class SceneDetails {

    private final Vector eye;
    private final double screenZCoordinate;
    private final Collection<SceneObject> objectsInScene;

    public SceneDetails(Vector eye, double screenZCoordinate, Collection<SceneObject> objectsInScene) {
        this.eye = eye;
        this.screenZCoordinate = screenZCoordinate;
        this.objectsInScene = objectsInScene;
    }

    public Vector getEye() {
        return eye;
    }

    public double getScreenZCoordinate() {
        return screenZCoordinate;
    }

    public Collection<SceneObject> getObjectsInScene() {
        return objectsInScene;
    }
}
