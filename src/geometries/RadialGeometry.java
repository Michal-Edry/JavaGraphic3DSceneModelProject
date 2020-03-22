package geometries;

/**
 * abstract class of radial geometry
 */
public abstract class RadialGeometry implements Geometry {
    protected double _radius;

    /**
     * constructor: gets a radius
     * @param _radius double
     */
    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

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
