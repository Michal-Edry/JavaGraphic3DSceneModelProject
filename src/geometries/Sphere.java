package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class of sphere
 */
public class Sphere extends RadialGeometry {

    protected Point3D _center;

    /**
     * constructor: gets a radius and a 3D point
     * @param _radius double
     * @param _center 3D point
     */
    public Sphere(double _radius, Point3D _center) {
        this(Color.BLACK,_radius,_center);
    }

    /**
     * constructor
     * @param _emission Color
     * @param _radius double
     * @param _center Point3D
     */
    public Sphere(Color _emission, double _radius, Point3D _center) {
        this(_emission,new Material(0,0,0),_radius,_center);
    }

    /**
     * constructor
     * @param _emission Color
     * @param _material Material
     * @param _radius double
     * @param _center Point3D
     */
    public Sphere(Color _emission, Material _material, double _radius, Point3D _center) {
        super(_emission,_radius);
        this._material = _material;
        this._center = new Point3D(_center);
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
     * get the normal to this sphere in a given point
     * @param _p 3D point
     * @return a normal
     */
    @Override
    public Vector getNormal(Point3D _p) {
        Vector orthogonal = new Vector(_p.subtract(_center));
        return orthogonal.normalized();
    }

    /**
     * gets a Ray and returns all the intersection points.
     * @param ray
     * @return List of Point3D
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Point3D p0 = ray.get_p0();
        Vector v = ray.get_dir();
        Vector u;
        try {
            u = _center.subtract(p0);   // p0 == _center
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this, ray.getPoint(_radius)));
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(_radius * _radius - dSquared);

        if(Math.sqrt(dSquared) > _radius) return null;

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))); //P1 , P2
        if (t1 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        else
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
    }
}
