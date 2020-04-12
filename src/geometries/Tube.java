package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

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
        this._axisRay = new Ray(_axisRay);
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
    @Override
    public Vector getNormal(Point3D _p) {
        //The vector from the point of the cylinder to the given point
        Point3D o = new Point3D(_axisRay.get_p0());
        Vector v = new Vector(_axisRay.get_dir());

        Vector vector1 = _p.subtract(o);

        //We need the projection to multiply the _direction unit vector
        double projection = vector1.dotProduct(v);
        if(!isZero(projection))
        {
            // projection of P-O on the ray:
            o.add(v.scale(projection));
        }

        //This vector is orthogonal to the _direction vector.
        Vector check = _p.subtract(o);
        return check.normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
