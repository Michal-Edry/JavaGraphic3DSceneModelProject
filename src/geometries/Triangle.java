package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
        super(_p1, _p2, _p3);
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
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersections = _plane.findIntersections(ray);
        if (intersections == null) return null;

        Point3D p0 = ray.get_p0();
        Vector v = ray.get_dir();

        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;

        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;

    }
}
