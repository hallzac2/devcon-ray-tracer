package zhall.devcon.ray.tracer.generator;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import java.util.stream.IntStream;
import zhall.devcon.ray.tracer.lighting.LightingFunction;
import zhall.devcon.ray.tracer.lighting.PhongLighting;
import zhall.devcon.ray.tracer.model.Intersection;
import zhall.devcon.ray.tracer.model.scene.SceneDetails;
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
public class FunctionalRayTracingPixelGenerator implements PixelGenerator {

    private final SceneDetails scene;
    private final LightingFunction lighting;
    private final Vector backgroundColor;
    private final int maxRecursionDepth;

    public FunctionalRayTracingPixelGenerator(SceneDetails scene) {
        this(scene, new PhongLighting(), new Vector(0.0, 0.0, 0.0), 1000);
    }

    public FunctionalRayTracingPixelGenerator(SceneDetails scene, LightingFunction lighting, Vector backgroundColor, int maxRecursionDepth) {
        this.scene = scene;
        this.lighting = lighting;
        this.backgroundColor = backgroundColor;
        this.maxRecursionDepth = maxRecursionDepth;
    }

    @Override
    public Map<Point, Color> getPixels(int width, int height) {
        return IntStream.range(0, width)
                .mapToObj(x -> IntStream.range(0, height).mapToObj(y -> new Point(x, y)))
                .flatMap(identity())
                .collect(toMap(identity(), getColorAtPoint(width, height)));
    }

    private Function<Point, Color> getColorAtPoint(int width, int height) {
        return p -> {
            double px = Coordinates.normalize(p.x, width);
            double py = Coordinates.normalize(width - p.y, height);
            Vector screenPoint = new Vector(px, py, scene.getScreenZCoordinate());
            Vector direction = subtract(screenPoint, scene.getEye());
            return vectorToColor(trace(screenPoint, direction, 0));
        };
    }

    private Vector trace(Vector point, Vector direction, int recursionDepth) {
        if (recursionDepth <= maxRecursionDepth) {
            return scene.getObjectsInScene().stream()
                    .map(obj -> obj.determineIntersection(point, direction))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .min((i1, i2) -> Double.compare(i1.gettValue(), i2.gettValue()))
                    .filter(intersection -> !intersection.getSceneObj().isLightSource())
                    .map(determineColorAtIntersection(direction, recursionDepth))
                    .orElse(backgroundColor);
        }
        return backgroundColor;
    }

    private Function<Intersection, Vector> determineColorAtIntersection(Vector direction, int recursionDepth) {
        return intersection -> {
            Vector reflection = reflect(direction, intersection.getNormalAtIntersection());
            Vector localColor = lighting.calculate(scene.getObjectsInScene(), scene.getEye(), intersection);
            Vector nextStartingPoint = createParametricPoint(intersection.getIntersectionPoint(), reflection, 0.1);
            Vector reflectedColor = trace(nextStartingPoint, reflection, recursionDepth + 1);
            // TODO Vector transmissionColor = transmit(direction, intersection.point, intersection.normal, refractionIndex);

            double depthReflectiveScaler = ((maxRecursionDepth - recursionDepth) / maxRecursionDepth) * intersection.getSceneObj().getReflectiveConstant();
            Vector finalReflectedColor = scale(depthReflectiveScaler, reflectedColor);

            return add(localColor, finalReflectedColor); // + transmissionColor
        };
    }
}
