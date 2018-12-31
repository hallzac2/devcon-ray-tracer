package zhall.devcon.ray.tracer.model;

import zhall.devcon.ray.tracer.util.Vector;

/**
 *
 * @author zhall
 */
public class LightProperties {

    private final Vector ambient;
    private final Vector diffuse;
    private final Vector specular;
    private final double alpha;

    public LightProperties(Vector ambient, Vector diffuse, Vector specular, double alpha) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.alpha = alpha;
    }

    public Vector getAmbient() {
        return ambient;
    }

    public Vector getDiffuse() {
        return diffuse;
    }

    public Vector getSpecular() {
        return specular;
    }

    public double getAlpha() {
        return alpha;
    }
}
