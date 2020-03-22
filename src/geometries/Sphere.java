package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class of sphere
 */
public class Sphere extends RadialGeometry {

    protected Point3D _center;

    /**
     * constrructor: gets a radius and a 3D point
     * @param _radius double
     * @param _center 3D point
     */
    public Sphere(double _radius, Point3D _center) {
        super(_radius);
        this._center = _center;
    }

    /**
     * getter for center
     * @return point3D
     */
    public Point3D get_center() {
        return _center;
    }
    /**
     * to string
     * @return a string with the sphere details
     */
    @Override
    public String toString() {
        return "" + _center + " " +_radius;
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
