package primitives;

import java.util.Objects;

public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    public final static Point3D ZERO = new Point3D(new Coordinate(0.0),new Coordinate(0.0),new Coordinate(0.0));

    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    public Point3D(double _x, double _y, double _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    public Point3D(Point3D _p) {
        this._x = _p.get_x();
        this._y = _p.get_y();
        this._z = _p.get_z();
    }

    public Coordinate get_x() {
        return _x;
    }

    public Coordinate get_y() {
        return _y;
    }

    public Coordinate get_z() {
        return _z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "" + _x + " " + _y + " " + _z;

    }

    public Vector subtract(Point3D _p){
        return new Vector(new Point3D(_p.get_x()._coord - this.get_x()._coord ,_p.get_y()._coord - this.get_y()._coord,_p.get_z()._coord - this.get_z()._coord));
    }

    public Point3D add(Vector _v){
        return new Point3D(this.get_x()._coord + _v.get_head().get_x()._coord, this.get_y()._coord + _v.get_head().get_y()._coord, this.get_z()._coord + _v.get_head().get_z()._coord);
    }

    public double distanceSquared(Point3D _p){
        return ((_p.get_x()._coord - this.get_x()._coord)*(_p.get_x()._coord - this.get_x()._coord) + (_p.get_y()._coord - this.get_y()._coord)*(_p.get_y()._coord - this.get_y()._coord) + (_p.get_z()._coord - this.get_z()._coord)*(_p.get_z()._coord - this.get_z()._coord));
    }

    public double distance(Point3D _p){
        return Math.sqrt(this.distanceSquared(_p));
    }
}
