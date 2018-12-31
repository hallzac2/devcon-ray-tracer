package zhall.devcon.ray.tracer.model;

import zhall.devcon.ray.tracer.model.scene.shape.SceneObject;
import zhall.devcon.ray.tracer.util.Vector;
import static zhall.devcon.ray.tracer.util.Vector.createParametricPoint;

/**
 *
 * @author zhall
 */
public class Intersection {

    private final Vector originatingPoint;
    private final Vector direction;
    private final double tValue;
    private final SceneObject sceneObj;
    private final Vector intersectionPoint;
    private final Vector normalAtIntersection;

    public Intersection(Vector originatingPoint, Vector direction, double tValue, SceneObject sceneObj) {
        this.originatingPoint = originatingPoint;
        this.direction = direction;
        this.tValue = tValue;
        this.sceneObj = sceneObj;
        this.intersectionPoint = createParametricPoint(originatingPoint, direction, tValue);
        this.normalAtIntersection = this.sceneObj.getNormalAtPoint(intersectionPoint);
    }

    public Vector getOriginatingPoint() {
        return originatingPoint;
    }

    public Vector getDirection() {
        return direction;
    }

    public double gettValue() {
        return tValue;
    }

    public SceneObject getSceneObj() {
        return sceneObj;
    }

    public Vector getIntersectionPoint() {
        return intersectionPoint;
    }

    public Vector getNormalAtIntersection() {
        return normalAtIntersection;
    }
}
