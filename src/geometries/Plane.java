package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry{

    Point3D _p;
    Vector _normal;

    public Plane(Point3D _p1, Point3D _p2, Point3D _p3){
        this._p = _p1;
        this._normal = null;
    }

    public Plane(Point3D _p, Vector _normal) {
        this._p = _p;
        this._normal = _normal;
    }

    public Point3D get_p() {
        return _p;
    }

    public Vector getNormal() {
        return _normal;
    }

    @Override
    public String toString() {
        return "" + _p + " " + _normal;
    }

    public Vector getNormal(Point3D _p){
        return this.getNormal();
    }


}
