package primitives;

import java.util.Objects;

/**
 * class of ray
 */
public class Ray {
   protected Point3D _p0;
   protected Vector _dir;

    /**
     * constructor: gets a 3D piont and a vector
     * @param _p0 3D point
     * @param _dir vector
     */
    public Ray(Point3D _p0, Vector _dir) {
        if (Math.sqrt((_dir.get_head().get_x()._coord - _p0.get_x()._coord)*(_dir.get_head().get_x()._coord - _p0.get_x()._coord) + (_dir.get_head().get_y()._coord - _p0.get_y()._coord)*(_dir.get_head().get_y()._coord - _p0.get_y()._coord) + (_dir.get_head().get_z()._coord - _p0.get_z()._coord)*(_dir.get_head().get_z()._coord - _p0.get_z()._coord)) == 1)
            throw new IllegalArgumentException("Illegal input");
        this._p0 = _p0;
        this._dir = _dir;
    }

    /**
     * getter fo p0
     * @return 3D point
     */
    public Point3D get_p0() {
        return _p0;
    }

    /**
     * getter for dir
     * @return a vector
     */
    public Vector get_dir() {
        return _dir;
    }


    /**
     * checks if 2 rays are equal
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
     * @return a string with the ray details
     */
    @Override
    public String toString() {
        return "" + _p0 + " " + _dir;
    }
}
