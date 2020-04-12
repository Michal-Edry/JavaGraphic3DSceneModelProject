package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class of plane
 */
public class Plane implements Geometry{

    protected Point3D _p;
    protected Vector _normal;

    /**
     * constructor: gets 3 points
     * @param _p1 3D point
     * @param _p2 3D point
     * @param _p3 3D point
     */
    public Plane(Point3D _p1, Point3D _p2, Point3D _p3){
        _p = new Point3D(_p1);

        Vector U = new Vector(_p1.subtract(_p2));
        Vector V = new Vector(_p1.subtract(_p3));
        Vector N = U.crossProduct(V);
        N.normalize();

        _normal = N.scale(-1);
    }

    /**
     * constructor: gets a 3D point and a vector
     * @param _p 3D point
     * @param _normal vector
     */
    public Plane(Point3D _p, Vector _normal) {
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
    }

    /**
     * getter for p
     * @return
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * getter for normal
     * @return
     */
    public Vector getNormal() {
        return _normal;
    }
    /**
     * to string
     * @return a string with the plane details
     */
    @Override
    public String toString() {
        return "" + _p + " " + _normal;
    }
    /**
     * returns the normal
     * @param _p 3D point
     * @return a normal
     */
    public Vector getNormal(Point3D _p){
        return this.getNormal();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
