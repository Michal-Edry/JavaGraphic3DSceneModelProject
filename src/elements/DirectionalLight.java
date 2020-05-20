package elements;

import primitives.*;

public class DirectionalLight extends Light implements LightSource  {
    private Vector _direction;

    /**
     * constructor
     * @param _intensity Color
     * @param _direction Vector
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = _direction.normalized();
    }

    /**
     * Get light source intensity as it reaches a point I<sub>P</sub>
     *
     * @param p the lighted point
     * @return intensity I<sub>P</sub>
     */
    @Override
    public Color getIntensity(Point3D p){
        return _intensity;
    }

    /**
     * Get normalized vector in the direction from light source
     * towards the lighted point
     *
     * @param p the lighted point
     * @return light to point vector
     */
    @Override
    public Vector getL(Point3D p){
        return _direction;
    }

    /**
     * calculates the distance between the light source to the point
     * @param point Point3D
     * @return double
     */
    public double getDistance(Point3D point){
        return Double.POSITIVE_INFINITY;
    }
}
