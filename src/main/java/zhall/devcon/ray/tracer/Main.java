package zhall.devcon.ray.tracer;

import java.awt.Canvas;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.JFrame;
import zhall.devcon.ray.tracer.generator.FunctionalRayTracingPixelGenerator;
import zhall.devcon.ray.tracer.generator.PixelGenerator;
import zhall.devcon.ray.tracer.model.LightProperties;
import zhall.devcon.ray.tracer.model.scene.SceneDetails;
import zhall.devcon.ray.tracer.model.scene.shape.Plane;
import zhall.devcon.ray.tracer.model.scene.shape.SceneObject;
import zhall.devcon.ray.tracer.model.scene.shape.Sphere;
import zhall.devcon.ray.tracer.util.Vector;

/**
 *
 * @author zhall
 */
public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel Art");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // PixelGenerator generator = new RandomPixelGenerator(r -> new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
        // PixelGenerator generator = new TraditionalRayTracingPixelGenerator(getScene());
        PixelGenerator generator = new FunctionalRayTracingPixelGenerator(getScene());

        int width = 800;
        int height = 800;
        Canvas canvas = new PixelCanvas(generator.getPixels(width, height));
        canvas.setBackground(Color.BLACK);
        canvas.setSize(width, height);

        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(frame);
        frame.setVisible(true);
    }

    public static SceneDetails getScene() {
        LightProperties lightProps1 = new LightProperties(new Vector(0.3, 0.0, 0.0), new Vector(0.5, 0.0, 0.0), new Vector(0.5, 0.5, 0.5), 75.0);
        SceneObject sphere1 = new Sphere(lightProps1, 0.15, false, new Vector(1.0, 2.0, 35.0), 2);

        LightProperties lightProps2 = new LightProperties(new Vector(1.0, 1.0, 1.0), new Vector(1.0, 1.0, 1.0), new Vector(1.0, 1.0, 1.0), 75.0);
        SceneObject sphere2 = new Sphere(lightProps2, 0.15, true, new Vector(5.0, 30.0, 10.0), 0.3);

        LightProperties lightProps3 = new LightProperties(new Vector(0.0, 0.3, 0.0), new Vector(0.0, 0.5, 0.0), new Vector(0.5, 0.5, 0.5), 75.0);
        SceneObject sphere3 = new Sphere(lightProps3, 0.15, false, new Vector(-3.0, 4.0, 35.0), 1.5);

        LightProperties lightProps4 = new LightProperties(new Vector(0.0, 0.0, 0.3), new Vector(0.0, 0.0, 0.5), new Vector(0.5, 0.5, 0.5), 75.0);
        SceneObject sphere4 = new Sphere(lightProps4, 0.15, false, new Vector(-3.0, 1.0, 35.0), 0.6);

        LightProperties lightProps5 = new LightProperties(new Vector(0.3, 0.3, 0.0), new Vector(0.5, 0.5, 0.0), new Vector(0.5, 0.5, 0.5), 75.0);
        SceneObject sphere5 = new Sphere(lightProps5, 0.15, false, new Vector(3.5, 3.5, 30.0), 0.75);

        LightProperties lightProps6 = new LightProperties(new Vector(0.02, 0.02, 0.02), new Vector(0.5, 0.5, 0.5), new Vector(0.5, 0.5, 0.5), 10.0);
        SceneObject plane = new Plane(lightProps6, 0.3, false, new Vector(0.0, -1.0, 0.0), new Vector(0.0, 1.0, 0.0));

        return new SceneDetails(new Vector(0.0, 0.0, 0.0), 5.0, Arrays.asList(sphere1, sphere2, sphere3, sphere4, sphere5, plane));
    }
}
