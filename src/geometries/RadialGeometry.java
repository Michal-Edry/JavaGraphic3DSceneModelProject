package geometries;

import primitives.Color;
import primitives.Material;

import static primitives.Util.isZero;

/**
 * abstract class of radial geometry
 */
public abstract class RadialGeometry extends Geometry {
    protected double _radius;

    /**
     * constructor: gets a radius
     * @param _radius double
     */
    public RadialGeometry(double _radius) {
        this(Color.BLACK, _radius);
    }

    /**
     * constructor
     * @param _emission Color
     * @param _radius double
     */
    public RadialGeometry(Color _emission, double _radius) {
        super(_emission);
        if (isZero(_radius) || (_radius < 0.0))
            throw new IllegalArgumentException("radius "+ _radius +" is not valid");
        this._radius = _radius;    }



    /**
     * copy constructor: gets a radial geometry
     * @param _r RadialGeometry
     */
    public RadialGeometry(RadialGeometry _r) {
        this._radius = _r._radius;
    }

    /**
     * getter for radius
     * @return double
     */
    public double get_radius() {
        return _radius;
    }

}
