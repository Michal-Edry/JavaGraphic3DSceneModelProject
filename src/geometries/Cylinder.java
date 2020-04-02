package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.awt.geom.Area;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class of cylinder
 */
public class Cylinder extends Tube {

    protected double _height;
    

    /**
     * constructor: gets a radius and height
     * @param _radius double    
     * @param _axisRay Ray
     * @param _height double
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        super(_radius, _axisRay);
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
    @Override
    public Vector getNormal(Point3D _p) {
        Point3D o = new Point3D(_axisRay.get_p0());
        Vector v = new Vector(_axisRay.get_dir());

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(_p.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
        { //return v;
            if(t == 0)
                return v.scale(-1);
            return v;
        }

        o = o.add(v.scale(t));
        return _p.subtract(o).normalize();
    }
}
