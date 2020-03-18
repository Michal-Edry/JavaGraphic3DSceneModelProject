package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {

    Ray _axisRay;

    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = _axisRay;
    }

    public Ray get_axisRay() {
        return _axisRay;
    }

    @Override
    public String toString() {
        return "" +_axisRay +" " + _radius;
    }
    public Vector getNormal(Point3D _p){
        return null;
    }
}
