package geometries;

import primitives.Point3D;

public class Triangle extends Polygon{
    public Triangle(Point3D _p1, Point3D _p2, Point3D _p3) {
        super(_p1, _p2, _p3);
    }

    @Override
    public String toString() {
        return "" + _vertices + " " + _plane;
    }
}
