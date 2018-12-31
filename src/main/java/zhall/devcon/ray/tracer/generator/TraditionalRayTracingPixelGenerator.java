package zhall.devcon.ray.tracer.generator;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import zhall.devcon.ray.tracer.lighting.LightingFunction;
import zhall.devcon.ray.tracer.lighting.PhongLighting;
import zhall.devcon.ray.tracer.model.Intersection;
import zhall.devcon.ray.tracer.model.scene.SceneDetails;
import zhall.devcon.ray.tracer.model.scene.shape.SceneObject;
import static zhall.devcon.ray.tracer.util.Colors.vectorToColor;
import zhall.devcon.ray.tracer.util.Coordinates;
import zhall.devcon.ray.tracer.util.Vector;
import static zhall.devcon.ray.tracer.util.Vector.add;
import static zhall.devcon.ray.tracer.util.Vector.createParametricPoint;
import static zhall.devcon.ray.tracer.util.Vector.reflect;
import static zhall.devcon.ray.tracer.util.Vector.scale;
import static zhall.devcon.ray.tracer.util.Vector.subtract;

/**
 *
 * @author zhall
 */
public class TraditionalRayTracingPixelGenerator implements PixelGenerator {

    private final SceneDetails scene;
    private final LightingFunction lighting;
    private final Vector backgroundColor;
    private final int maxRecursionDepth;

    public TraditionalRayTracingPixelGenerator(SceneDetails scene) {
        this(scene, new PhongLighting(), new Vector(0.0, 0.0, 0.0), 1000);
    }

    public TraditionalRayTracingPixelGenerator(SceneDetails scene, LightingFunction lighting, Vector backgroundColor, int maxRecursionDepth) {
        this.scene = scene;
        this.lighting = lighting;
        this.backgroundColor = backgroundColor;
        this.maxRecursionDepth = maxRecursionDepth;
    }

    @Override
    public Map<Point, Color> getPixels(int width, int height) {
        Map<Point, Color> colorForPoint = new HashMap<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Point p = new Point(x, y);
                Color c = getColorAtPoint(width, height, p);
                colorForPoint.put(p, c);
            }
        }
        return colorForPoint;
    }

    private Color getColorAtPoint(int width, int height, Point p) {
        double px = Coordinates.normalize(p.x, width);
        double py = Coordinates.normalize(width - p.y, height);
        Vector screenPoint = new Vector(px, py, scene.getScreenZCoordinate());
        Vector direction = subtract(screenPoint, scene.getEye());
        return vectorToColor(trace(screenPoint, direction, 0));
    }

    private Vector trace(Vector point, Vector direction, int recursionDepth) {
        if (recursionDepth <= maxRecursionDepth) {
            Intersection closest = null;
            for (SceneObject obj : scene.getObjectsInScene()) {
                Intersection i = obj.determineIntersection(point, direction).orElse(null);
                if (i != null) {
                    closest = (closest == null || closest.gettValue() > i.gettValue()) ? i : closest;
                }
            }

            if (closest != null && !closest.getSceneObj().isLightSource()) {
                return determineColorAtIntersection(direction, recursionDepth, closest);
            }
        }
        return backgroundColor;
    }

    private Vector determineColorAtIntersection(Vector direction, int recursionDepth, Intersection intersection) {
        Vector reflection = reflect(direction, intersection.getNormalAtIntersection());
        Vector localColor = lighting.calculate(scene.getObjectsInScene(), scene.getEye(), intersection);
        Vector nextStartingPoint = createParametricPoint(intersection.getIntersectionPoint(), reflection, 0.1);
        Vector reflectedColor = trace(nextStartingPoint, reflection, recursionDepth + 1);
        // TODO Vector transmissionColor = transmit(direction, intersection.point, intersection.normal, refractionIndex);

        double depthReflectiveScaler = ((maxRecursionDepth - recursionDepth) / maxRecursionDepth) * intersection.getSceneObj().getReflectiveConstant();
        Vector finalReflectedColor = scale(depthReflectiveScaler, reflectedColor);

        return add(localColor, finalReflectedColor); // + transmissionColor
    }
}
