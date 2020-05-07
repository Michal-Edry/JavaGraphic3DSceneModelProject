package elements;

import primitives.*;

public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC, _kL, _kQ;

    /**
     * constructor
     * @param _intensity Color
     * @param _position Point3D
     * @param _kC double
     * @param _kL double
     * @param _kQ double
     */
    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intensity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    /**
     * Get light source intensity as it reaches a point I<sub>P</sub>
     *
     * @param p the lighted point
     * @return intensity I<sub>P</sub>
     */
    public Color getIntensity(Point3D p){
        double distance = _position.distance(p);
        double denominator = _kC + _kL*distance + _kQ*distance*distance;
        Color il = _intensity.reduce(denominator);
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
        return null;
    }
}
