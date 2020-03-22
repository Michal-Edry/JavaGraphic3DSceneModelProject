package primitives;

import java.util.Objects;

/**
 * Class of 3D point
 */
public class Point3D {
    protected Coordinate _x;
    protected Coordinate _y;
    protected Coordinate _z;

    public final static Point3D ZERO = new Point3D(new Coordinate(0.0),new Coordinate(0.0),new Coordinate(0.0));

    /**
     * Constructor: gets 3 coordinates
     * @param _x coordinate x
     * @param _y coordinate y
     * @param _z coordinate z
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    /**
     * Constructor: gets 3 doubles
     * @param _x coordinate x
     * @param _y coordinate y
     * @param _z coordinate z
     */
    public Point3D(double _x, double _y, double _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     * copy constructor: gets a point
     * @param _p
     */
    public Point3D(Point3D _p) {
        this._x = _p.get_x();
        this._y = _p.get_y();
        this._z = _p.get_z();
    }

    /**
     * getter for x
     * @return coordinate x
     */
    public Coordinate get_x() {
        return _x;
    }
    /**
     * getter for y
     * @return coordinate y
     */
    public Coordinate get_y() {
        return _y;
    }
    /**
     * getter for z
     * @return coordinate z
     */
    public Coordinate get_z() {
        return _z;
    }

    /**
     * checks if 2 3D points are equal
     * @param o gets a 3D point
     * @return if the 3D piont are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    /**
     * to string
     * @return a string with the point details
     */
    @Override
    public String toString() {
        return "" + _x + " " + _y + " " + _z;

    }

    /**
     * subtracts 2 3D points and returns a vector that starts with the point that called the func
     * @param _p gets a 3D point
     * @return a vector
     */
    public Vector subtract(Point3D _p){
        return new Vector(new Point3D(_p.get_x()._coord - this.get_x()._coord ,_p.get_y()._coord - this.get_y()._coord,_p.get_z()._coord - this.get_z()._coord));
    }

    /**
     * adds a vector to a 3D point
     * @param _v gets a vector
     * @return a 3D point
     */
    public Point3D add(Vector _v){
        return new Point3D(this.get_x()._coord + _v.get_head().get_x()._coord, this.get_y()._coord + _v.get_head().get_y()._coord, this.get_z()._coord + _v.get_head().get_z()._coord);
    }

    /**
     * calculates the squared distance between 2 points
     * @param _p gets a 3D point
     * @return the squared distance
     */
    public double distanceSquared(Point3D _p){
        return ((_p.get_x()._coord - this.get_x()._coord)*(_p.get_x()._coord - this.get_x()._coord) + (_p.get_y()._coord - this.get_y()._coord)*(_p.get_y()._coord - this.get_y()._coord) + (_p.get_z()._coord - this.get_z()._coord)*(_p.get_z()._coord - this.get_z()._coord));
    }

    /**
     * returns the distance between 2 3D points
     * @param _p gets a 3D point
     * @return the distance
     */
    public double distance(Point3D _p){
        return Math.sqrt(this.distanceSquared(_p));
    }
}
