package primitives;

import java.util.Objects;

/**
 * class of vector
 */
public class Vector {
    protected Point3D _head;

    /**
     * constructor: gets a 3D point
     * @param _head 3D point
     */
    public Vector(Point3D _head) {
        this._head = _head;
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Illegal input");
    }

    /**
     * constructor: gets 3 coordinates
     * @param _x coordinate x
     * @param _y coordinate y
     * @param _z coordinate z
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._head._x = _x;
        this._head._y = _y;
        this._head._z = _z;
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Illegal input");
    }

    /**
     * constructor: gets 3 doubles
     * @param _x double x
     * @param _y double y
     * @param _z double z
     */
    public Vector(double _x, double _y, double _z) {
        try {
            this._head = new Point3D(_x, _y, _z);
            if (_head.equals(Point3D.ZERO))
                throw new IllegalArgumentException("Illegal input");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    /**
     * copy constructor: gets a vector
     * @param _v vector
     */
    public Vector(Vector _v) {
        this._head = new Point3D(_v.get_head());
    }

    /**
     * getter for head
     * @return a 3D point
     */
    public Point3D get_head() {
        return _head;
    }

    /**
     * checks if 2 vectors are equal
     * @param o gets a vector
     * @return if the vectors are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }
    /**
     * to string
     * @return a string with the vector details
     */
    @Override
    public String toString() {
        return "" + _head;
    }

    /**
     * subtracts a vector from our vector
     * @param _v gets a vector
     * @return a new vector
     */
    public Vector subtract(Vector _v) {
        return new Vector(new Point3D(_v._head.get_x()._coord - this._head.get_x()._coord, _v._head.get_y()._coord - this._head.get_y()._coord, _v._head.get_z()._coord - this._head.get_z()._coord));
    }

    /**
     * adds a vector to our vector
     * @param _v gets a vector
     * @return a vector
     */
    public Vector add(Vector _v) {
        return new Vector(new Point3D(_v._head.get_x()._coord + this._head.get_x()._coord, _v._head.get_y()._coord + this._head.get_y()._coord, _v._head.get_z()._coord + this._head.get_z()._coord));
    }

    /**
     * multiples a vector by a number
     * @param _a int number
     * @return a multiplied vector
     */
    public Vector scale(int _a) {
        return new Vector(new Point3D(this._head.get_x()._coord * _a, this._head.get_y()._coord * _a, this._head.get_z()._coord * _a));
    }

    /**
     * dot product on 2 vectors
     * @param _v gets a vector
     * @return a double number
     */
    public Double dotProduct(Vector _v) {
        return (this._head.get_x()._coord * _v._head.get_x()._coord + this._head.get_y()._coord * _v._head.get_y()._coord + this._head.get_z()._coord * _v._head.get_z()._coord);
    }

    /**
     * cross product on 2 vectors
     * @param _v gets a vector
     * @return a vector
     */
    public Vector crossProduct(Vector _v){
        return new Vector(new Point3D(this._head.get_y()._coord*_v._head.get_z()._coord - this._head.get_z()._coord*_v._head.get_y()._coord,this._head.get_z()._coord*_v._head.get_x()._coord - this._head.get_x()._coord*_v._head.get_z()._coord, this._head.get_x()._coord*_v._head.get_y()._coord - this._head.get_y()._coord*_v._head.get_x()._coord));
    }

    /**
     * calculates the squared length of a vector
     * @return the squared length
     */
    public double LengthSquared()
    {
        return Point3D.ZERO.distanceSquared(this.get_head());
    }

    /**
     * calculates the length of a vector
     * @return the length
     */
    public double length(){
        return  Math.sqrt(this.LengthSquared());
    }

    /**
     * normalizes the vector
     * @return our vector after changed
     */
    public  Vector normalize()
    {
        Vector _v=new Vector(new Point3D(this._head._x._coord/this.length(),this._head._y._coord/this.length(),this._head._z._coord/this.length()));
        this._head=_v.get_head();
        return  this;
    }

    /**
     * returns a new normalized vector with the direction of head
     * @return a normalized vector
     */
    public Vector normalized(){
        Vector _v = new Vector(this._head);
        return _v.normalize();
    }
}