package zhall.devcon.ray.tracer.util;

import java.awt.Color;

/**
 *
 * @author zhall
 */
public class Colors {

    private Colors() {
    }

    public static Color vectorToColor(Vector v) {
        float r = Math.min((float) v.getX(), 1.0f);
        float g = Math.min((float) v.getY(), 1.0f);
        float b = Math.min((float) v.getZ(), 1.0f);

        return new Color(r, g, b);
    }
}
