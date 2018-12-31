package zhall.devcon.ray.tracer.lighting;

import java.util.Collection;
import zhall.devcon.ray.tracer.model.Intersection;
import zhall.devcon.ray.tracer.model.LightProperties;
import zhall.devcon.ray.tracer.model.scene.shape.SceneObject;
import zhall.devcon.ray.tracer.util.Vector;
import static zhall.devcon.ray.tracer.util.Vector.add;
import static zhall.devcon.ray.tracer.util.Vector.dot;
import static zhall.devcon.ray.tracer.util.Vector.multiply;
import static zhall.devcon.ray.tracer.util.Vector.normalize;
import static zhall.devcon.ray.tracer.util.Vector.scale;
import static zhall.devcon.ray.tracer.util.Vector.subtract;

/**
 *
 * @author zhall
 */
public class PhongLighting implements LightingFunction {

    @Override
    public Vector calculate(Collection<SceneObject> objectsInScene, Vector eye, Intersection intersection) {
        Vector color = objectsInScene.stream()
                .filter(SceneObject::isLightSource)
                .map(obj -> getPhongColor(obj, eye, intersection))
                .reduce(new Vector(0, 0, 0), Vector::add);

        return normalizeColor(color);
    }

    private Vector getPhongColor(SceneObject obj, Vector eye, Intersection intersection) {
        LightProperties intersectedLight = intersection.getSceneObj().getLightProperties();
        LightProperties sceneObjectLight = obj.getLightProperties();

        double alpha = intersectedLight.getAlpha();
        Vector ka = intersectedLight.getAmbient();
        Vector kd = intersectedLight.getDiffuse();
        Vector ks = intersectedLight.getSpecular();

        Vector la = sceneObjectLight.getAmbient();
        Vector ld = sceneObjectLight.getDiffuse();
        Vector ls = sceneObjectLight.getSpecular();

        Vector l = normalize(subtract(obj.getCenter(), intersection.getIntersectionPoint()));
        Vector n = normalize(intersection.getNormalAtIntersection());
        Vector v = normalize(subtract(eye, intersection.getIntersectionPoint()));
        Vector h = normalize(add(l, v));

        double diffuseDot = Math.max(dot(l, n), 0.0);
        double specularDot = Math.max(Math.pow(dot(n, h), alpha), 0.0);

        Vector ambient = multiply(ka, la);
        Vector diffuse = scale(diffuseDot, multiply(kd, ld));
        Vector specular = scale(specularDot, multiply(ks, ls));

        return add(add(ambient, diffuse), specular);
    }

    private Vector normalizeColor(Vector v) {
        double x = Math.min(v.getX(), 1.0);
        double y = Math.min(v.getY(), 1.0);
        double z = Math.min(v.getZ(), 1.0);

        return new Vector(x, y, z);
    }
}
