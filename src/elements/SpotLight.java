package elements;

import primitives.*;

public class SpotLight extends PointLight{
    private Vector _direction;

    /**
     * constructor
     * @param _intensity Color
     * @param _position Point3D
     * @param _kC double
     * @param _kL double
     * @param _kQ double
     * @param _direction Vector
     */
    public SpotLight(Color _intensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        this._direction = new Vector(_direction.normalized());
    }

    /**
     * Get light source intensity as it reaches a point I<sub>P</sub>
     *
     * @param p the lighted point
     * @return intensity I<sub>P</sub>
     */
    public Color getIntensity(Point3D p){
        Color pointLightIntensity = super.getIntensity(p);
        double projection = _direction.dotProduct(getL(p));
        Color il = pointLightIntensity.scale(Math.max(0,projection));
        return il;
    }

    /**
     * Get normalized vector in the direction from light source
     * towards the lighted point
     *
     * @param p the lighted point
     * @return light to point vector
     */
    public Vector getL(Point3D p){
        return super.getL(p);
    }
}
