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
        this._direction = _direction;
    }

    /**
     * Get light source intensity as it reaches a point I<sub>P</sub>
     *
     * @param p the lighted point
     * @return intensity I<sub>P</sub>
     */
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
    public Vector getL(Point3D p){
        return _direction;
    }
}
