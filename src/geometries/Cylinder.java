package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Cylinder extends RadialGeometry {

    double _height;

    public Cylinder(double _radius, double _height) {
        super(_radius);
        this._height = _height;
    }

    public double get_height() {
        return _height;
    }

    @Override
    public String toString() {
        return "" +_height + " " + _radius;
    }
    public Vector getNormal(Point3D _p){
        return null;
    }
}
