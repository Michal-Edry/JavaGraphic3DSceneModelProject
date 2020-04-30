package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
     * @return Point3D
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * getter for normal
     * @return Vector
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

    /**
     * gets a Ray and returns all the intersection points.
     * @param ray
     * @return List of Point3D
     */
   @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector p0Q;
        try {
            p0Q = _p.subtract(ray.get_p0());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }

        double nv = _normal.dotProduct(ray.get_dir());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;

        double t = alignZero(_normal.dotProduct(p0Q) / nv);

        return t <= 0 ? null : List.of(ray.getPoint(t));
    }

}
