package geometries;

import primitives.*;

import java.util.List;

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
        this(Color.BLACK,_radius,_axisRay,_height);
    }

    /**
     * constructor
     * @param _emission Color
     * @param _radius double
     * @param _axisRay Ray
     * @param _height double
     */
    public Cylinder(Color _emission, double _radius, Ray _axisRay, double _height) {
        this(_emission,new Material(0,0,0),_radius,_axisRay,_height);
    }

    /**
     * constructor
     * @param _emission Color
     * @param _material Material
     * @param _radius double
     * @param _axisRay Ray
     * @param _height double
     */
    public Cylinder(Color _emission, Material _material, double _radius, Ray _axisRay, double _height) {
        super(_emission, _radius, _axisRay);
        this._material = _material;
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
        Point3D o = new Point3D(_axisRay.getP0());
        Vector v = new Vector(_axisRay.getDir());

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

    /**
     * gets a Ray and returns all the intersection points.
     * @param ray
     * @return List of Point3D
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}
