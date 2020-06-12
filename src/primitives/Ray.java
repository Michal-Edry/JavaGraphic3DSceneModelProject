package primitives;

import static primitives.Util.isZero;

/**
 * class of ray
 */
public class Ray {
    private static final double DELTA = 0.1;

    protected Point3D _p0;
    protected Vector _dir;

    /**
     * constructor: gets a 3D point and a vector
     *
     * @param _p0  3D point
     * @param _dir Vector
     */
    public Ray(Point3D _p0, Vector _dir) {
        if (_dir.length() != 1)
            _dir.normalize();
        this._p0 = new Point3D(_p0);
        this._dir = new Vector(_dir);
    }

    /**
     * copy constructor: gets a ray
     *
     * @param _ray Ray
     */
    public Ray(Ray _ray) {
        this._p0 = new Point3D(_ray.getP0());
        this._dir = new Vector(_ray.getDir());
    }

    /**
     * constructor
     * @param point Point3D
     * @param direction Vector
     * @param normal Vector
     */
    public Ray(Point3D point, Vector direction, Vector normal) {
        //point + normal.scale(±DELTA)
        _dir = new Vector(direction).normalized();

        double nv = normal.dotProduct(direction);

        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        _p0 = point.add(normalDelta);
    }


    /**
     * getter fo p0
     *
     * @return 3D point
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getter for dir
     *
     * @return a vector
     */
    public Vector getDir() {
        return _dir.normalized();
    }


    /**
     * checks if 2 rays are equal
     *
     * @param o gets a ray
     * @return if the rays are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) &&
                _dir.equals(ray._dir);
    }

    /**
     * to string
     *
     * @return a string with the ray details
     */
    @Override
    public String toString() {
        return "" + _p0 + " " + _dir;
    }

    /**
     * @param length
     * @return new Point3D
     */
    public Point3D getPoint(double length) {
        return isZero(length) ? _p0 : _p0.add(_dir.scale(length));
    }
}