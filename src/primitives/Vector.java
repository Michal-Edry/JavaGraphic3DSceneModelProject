package primitives;

import java.util.Objects;

public class Vector {
    Point3D _head;

    public Vector(Point3D _head) {
        try {
            if (_head.equals(Point3D.ZERO))
                throw new IllegalArgumentException("Illegal input");
            this._head = _head;
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
        try {
            if (_head.equals(Point3D.ZERO))
                throw new IllegalArgumentException("Illegal input");
            this._head._x = _x;
            this._head._y = _y;
            this._head._z = _z;

        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public Vector(double _x, double _y, double _z) {
        try {
            if (_head.equals(Point3D.ZERO))
                throw new IllegalArgumentException("Illegal input");
            this._head = new Point3D(_x, _y, _z);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public Vector(Vector _v) {
        try {
            if (_head.equals(Point3D.ZERO))
                throw new IllegalArgumentException("Illegal input");
            this._head = _v.get_head();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }


    public Point3D get_head() {
        return _head;
    }

    public void set_head(Point3D _head) {
        this._head = _head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return "" + _head;
    }

    public Vector Subtract(Vector _v) {
        return new Vector(new Point3D(_v._head.get_x()._coord - this._head.get_x()._coord, _v._head.get_y()._coord - this._head.get_y()._coord, _v._head.get_z()._coord - this._head.get_z()._coord));
    }

    public Vector Add(Vector _v) {
        return new Vector(new Point3D(_v._head.get_x()._coord + this._head.get_x()._coord, _v._head.get_y()._coord + this._head.get_y()._coord, _v._head.get_z()._coord + this._head.get_z()._coord));
    }

    public Vector Scale(int _a) {
        return new Vector(new Point3D(this._head.get_x()._coord * _a, this._head.get_y()._coord * _a, this._head.get_z()._coord * _a));
    }

    public Double DotProduct(Vector _v) {
        return (this._head.get_x()._coord * _v._head.get_x()._coord + this._head.get_y()._coord * _v._head.get_y()._coord + this._head.get_z()._coord * _v._head.get_z()._coord);
    }

    public Vector CrossProduct(Vector _v){
        return new Vector(new Point3D(this._head.get_y()._coord*_v._head.get_z()._coord - this._head.get_z()._coord*_v._head.get_y()._coord,this._head.get_z()._coord*_v._head.get_x()._coord - this._head.get_x()._coord*_v._head.get_z()._coord, this._head.get_x()._coord*_v._head.get_y()._coord - this._head.get_y()._coord*_v._head.get_x()._coord));
    }

    public double LengthSquared()
    {
        return Point3D.ZERO.DistanceSquared(this.get_head());
    }
    public double Length(){
        return  Math.sqrt(this.LengthSquared());
    }
    public  Vector Normalize()
    {
        Vector _v=new Vector(new Point3D(this._head._x._coord/this.Length(),this._head._y._coord/this.Length(),this._head._z._coord/this.Length()));
        this._head=_v.get_head();
        return  this;
    }

    public Vector Normalized(){
        Vector _v = new Vector(this._head);
        return _v.Normalize();
    }
}