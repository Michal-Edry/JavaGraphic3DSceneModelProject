package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere extends RadialGeometry {

    Point3D _center;

    public Sphere(double _radius, Point3D _center) {
        super(_radius);
        this._center = _center;
    }

    public Point3D get_center() {
        return _center;
    }

    @Override
    public String toString() {
        return "" + _center + " " +_radius;
    }
    public Vector getNormal(Point3D _p){
        return null;
    }

}
