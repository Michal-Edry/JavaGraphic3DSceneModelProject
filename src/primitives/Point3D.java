package primitives;

/**
 * Class of 3D point
 */
public class Point3D {
    protected Coordinate _x;
    protected Coordinate _y;
    protected Coordinate _z;

    public final static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

    /**
     * Constructor: gets 3 coordinates
     *
     * @param _x coordinate x
     * @param _y coordinate y
     * @param _z coordinate z
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this(_x._coord, _y._coord, _z._coord);
    }

    /**
     * Constructor: gets 3 doubles
     *
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
     *
     * @param _p Point3D
     */
    public Point3D(Point3D _p) {
        this(_p._x._coord,_p._y._coord,_p._z._coord);
//        this._x = new Coordinate(_p.getX());
//        this._y = new Coordinate(_p.getY());
//        this._z = new Coordinate(_p.getZ());
    }

    /**
     * getter for x
     *
     * @return coordinate x
     */
    public Coordinate getX() {
        return _x;
    }

    /**
     * getter for y
     *
     * @return coordinate y
     */
    public Coordinate getY() {
        return _y;
    }

    /**
     * getter for z
     *
     * @return coordinate z
     */
    public Coordinate getZ() {
        return _z;
    }

    /**
     * checks if 2 3D points are equal
     *
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
     *
     * @return a string with the point details
     */
    @Override
    public String toString() {
        return "" + _x + " " + _y + " " + _z;

    }

    /**
     * subtracts a vector feom a point3D
     *
     * @param v Vector v
     * @return Point3D
     */
    public Point3D subtract(Vector v) {
        return new Point3D(this._x._coord - v._head._x._coord,
                this._y._coord - v._head._y._coord,
                this._z._coord - v._head._z._coord);
    }

    /**
     * subtracts 2 3D points and returns a vector that starts with the point that called the func
     *
     * @param _p gets a 3D point
     * @return a vector
     */
    public Vector subtract(Point3D _p) {
        return new Vector(this._x._coord - _p.getX()._coord, this.getY()._coord - _p.getY()._coord, this.getZ()._coord - _p.getZ()._coord);
    }

    /**
     * adds a vector to a 3D point
     *
     * @param _v gets a vector
     * @return a 3D point
     */
    public Point3D add(Vector _v) {
        return new Point3D(this.getX()._coord + _v.getHead().getX()._coord, this.getY()._coord + _v.getHead().getY()._coord, this.getZ()._coord + _v.getHead().getZ()._coord);
    }

    /**
     * calculates the squared distance between 2 points
     *
     * @param _p gets a 3D point
     * @return the squared distance
     */
    public double distanceSquared(Point3D _p) {
        return ((_p.getX()._coord - this.getX()._coord) * (_p.getX()._coord - this.getX()._coord) + (_p.getY()._coord - this.getY()._coord) * (_p.getY()._coord - this.getY()._coord) + (_p.getZ()._coord - this.getZ()._coord) * (_p.getZ()._coord - this.getZ()._coord));
    }

    /**
     * returns the distance between 2 3D points
     *
     * @param _p gets a 3D point
     * @return the distance
     */
    public double distance(Point3D _p) {
        return Math.sqrt(this.distanceSquared(_p));
    }
}
