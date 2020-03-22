package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class of tube
 */
public class Tube extends RadialGeometry {

    protected Ray _axisRay;

    /**
     * constructor: gets a radius and a ray
     * @param _radius
     * @param _axisRay
     */
    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = _axisRay;
    }

    /**
     * getter for axisRay
     * @return a ray
     */
    public Ray get_axisRay() {
        return _axisRay;
    }
    /**
     * to string
     * @return a string with the tube details
     */
    @Override
    public String toString() {
        return "" +_axisRay +" " + _radius;
    }
    /**
     * returns the normal
     * @param _p 3D point
     * @return a normal
     */
    public Vector getNormal(Point3D _p){
        return null;
    }
}
