package geometries;

import primitives.Point3D;

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
}
