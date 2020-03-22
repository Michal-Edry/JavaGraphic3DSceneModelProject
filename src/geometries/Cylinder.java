package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class of cylinder
 */
public class Cylinder extends RadialGeometry {

    protected double _height;

    /**
     * constructor: gets a radius and height
     * @param _radius double
     * @param _height double
     */
    public Cylinder(double _radius, double _height) {
        super(_radius);
        this._height = _height;
    }

    /**
     * getter for height
     * @return double height
     */
    public double get_height() {
        return _height;
    }
    /**
     * to string
     * @return a string with the cylinder details
     */
    @Override
    public String toString() {
        return "" +_height + " " + _radius;
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
