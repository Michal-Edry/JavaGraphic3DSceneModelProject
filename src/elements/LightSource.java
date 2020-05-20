package elements;

import primitives.*;

/**
 * interface of Light Sources
 */
public interface LightSource {
    /**
     * Get light source intensity as it reaches a point I<sub>P</sub>
     *
     * @param p the lighted point
     * @return intensity I<sub>P</sub>
     */
    public Color getIntensity(Point3D p);

    /**
     * Get normalized vector in the direction from light source
     * towards the lighted point
     *
     * @param p the lighted point
     * @return light to point vector
     */
    public Vector getL(Point3D p);

    /**
     * calculates the distance between the light source to the point
     * @param point Point3D
     * @return double
     */
    public double getDistance(Point3D point);
}
