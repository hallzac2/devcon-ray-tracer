package zhall.devcon.ray.tracer.model.scene.shape;

import zhall.devcon.ray.tracer.model.scene.shape.SceneShape;
import zhall.devcon.ray.tracer.model.LightProperties;

/**
 *
 * @author zhall
 */
public abstract class SceneObject implements SceneShape {

    private final LightProperties lightProperties;
    private final double reflectiveConstant;
    private final boolean isLightSource;

    public SceneObject(LightProperties lightProperties, double reflectiveConstant, boolean isLightSource) {
        this.lightProperties = lightProperties;
        this.reflectiveConstant = reflectiveConstant;
        this.isLightSource = isLightSource;
    }

    public LightProperties getLightProperties() {
        return lightProperties;
    }

    public double getReflectiveConstant() {
        return reflectiveConstant;
    }

    public boolean isLightSource() {
        return isLightSource;
    }
}
