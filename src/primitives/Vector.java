package primitives;

/**
 * class of vector
 */
public class Vector {
    protected Point3D _head;

    /**
     * constructor: gets a 3D point
     * @param _head 3D point
     * @throws IllegalArgumentException
     */
    public Vector(Point3D _head) {
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Illegal input");
        this._head = new Point3D(_head);
     }

    /**
     * constructor: gets 3 coordinates
     * @param coord_x coordinate x
     * @param coord_y coordinate y
     * @param coord_z coordinate z
     *  throws IllegalArgumentException
     */
    public Vector(Coordinate coord_x, Coordinate coord_y, Coordinate coord_z) {
        this(coord_x._coord, coord_y._coord, coord_z._coord);
    }

    /**
     * constructor: gets 3 doubles
     * @param _x double x
     * @param _y double y
     * @param _z double z
     * @throws IllegalArgumentException
     */
    public Vector(double _x, double _y, double _z) {
        this._head = new Point3D(_x, _y, _z);
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Illegal input");
    }

    /**
     * copy constructor: gets a vector
     * @param _v vector
     */
    public Vector(Vector _v) {
        this._head = new Point3D(_v._head);
    }

    /**
     * getter for head
     * @return a 3D point
     */
    public Point3D getHead() {
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
        return new Vector(this._head._x._coord - _v._head._x._coord, this._head._y._coord - _v._head._y._coord, this._head._z._coord - _v._head._z._coord);
    }


    /**
     * adds a vector to our vector
     * @param _v gets a vector
     * @return a vector
     */
    public Vector add(Vector _v) {
        return new Vector(_v._head._x._coord + this._head._x._coord, _v._head._y._coord + this._head._y._coord, _v._head._z._coord + this._head._z._coord);
    }

    /**
     * multiples a vector by a number
     * @param _a int number
     * @return a multiplied vector
     */
    public Vector scale(double _a) {
        return new Vector(this._head._x._coord * _a, this._head._y._coord * _a, this._head._z._coord * _a);
    }

    /**
     * dot product on 2 vectors
     * @param _v gets a vector
     * @return a double number
     */
    public Double dotProduct(Vector _v) {
        return (this._head._x._coord * _v._head._x._coord + this._head._y._coord * _v._head._y._coord + this._head._z._coord * _v._head._z._coord);
    }

    /**
     * cross product on 2 vectors
     * @param _v gets a vector
     * @return a vector
     */
    public Vector crossProduct(Vector _v){
        return new Vector(this._head._y._coord*_v._head._z._coord - this._head._z._coord*_v._head._y._coord,this._head._z._coord*_v._head._x._coord - this._head._x._coord*_v._head._z._coord, this._head._x._coord*_v._head._y._coord - this._head._y._coord*_v._head._x._coord);
    }

    /**
     * calculates the squared length of a vector
     * @return the squared length
     */
    public double lengthSquared()
    {
        return Point3D.ZERO.distanceSquared(this.getHead());
    }

    /**
     * calculates the length of a vector
     * @return the length
     */
    public double length(){
        return  Math.sqrt(this.lengthSquared());
    }

    /**
     * normalizes the vector (changing the head source)
     * @return our vector after changed
     */
    public Vector normalize()
    {
        Vector _v = new Vector(new Point3D(this._head._x._coord/this.length(),this._head._y._coord/this.length(),this._head._z._coord/this.length()));
        this._head = new Point3D(_v.getHead());
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