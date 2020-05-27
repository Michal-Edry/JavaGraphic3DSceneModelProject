package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 * class of triangle
 */
public class Triangle extends Polygon{

    /**
     * constructor: gets 3 3D points
     * @param _p1 point3D
     * @param _p2 point3D
     * @param _p3 point3D
     */
    public Triangle(Point3D _p1, Point3D _p2, Point3D _p3) {
        this(Color.BLACK, _p1, _p2, _p3);
    }

    /**
     * constructor
     * @param _emission Color
     * @param _p1 point3D
     * @param _p2 point3D
     * @param _p3 point3D
     */
    public Triangle(Color _emission, Point3D _p1, Point3D _p2, Point3D _p3) {
        this(_emission, new Material(0,0,0) ,_p1, _p2, _p3);
    }

    /**
     *
     * @param _emission Color
     * @param _material Material
     * @param _p1 point3D
     * @param _p2 point3D
     * @param _p3 point3D
     */
    public Triangle(Color _emission, Material _material,Point3D _p1, Point3D _p2, Point3D _p3 ) {
        super(_emission, _material, _p1, _p2, _p3);
    }

    /**
     * to string
     * @return a string with the triangle details
     */
    @Override
    public String toString() {
        return "" + _vertices + " " + _plane;
    }

    /**
     * gets a Ray and returns all the intersection points.
     * @param ray
     * @return List of Point3D
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = _plane.findIntersections(ray);
        if (intersections == null) return null;

        Point3D p0 = ray.get_p0();
        Vector v = ray.get_dir();

        Vector v1 = _vertices.get(0).subtract(p0).normalized();
        Vector v2 = _vertices.get(1).subtract(p0).normalized();
        Vector v3 = _vertices.get(2).subtract(p0).normalized();

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;

        //for GeoPoint
        intersections.get(0)._geometry = this;


        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;

    }
}
