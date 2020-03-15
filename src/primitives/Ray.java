package primitives;

import java.util.Objects;

public class Ray {
   Point3D _p0;
   Vector _dir;

    public Ray(Point3D _p0, Vector _dir) {
        try {
            if (Math.sqrt((_dir.get_head().get_x()._coord - _p0.get_x()._coord)*(_dir.get_head().get_x()._coord - _p0.get_x()._coord) + (_dir.get_head().get_y()._coord - _p0.get_y()._coord)*(_dir.get_head().get_y()._coord - _p0.get_y()._coord) + (_dir.get_head().get_z()._coord - _p0.get_z()._coord)*(_dir.get_head().get_z()._coord - _p0.get_z()._coord)) == 1)
                throw new IllegalArgumentException("Illegal input");
            this._p0 = _p0;
            this._dir = _dir;
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }
    }

    public Point3D get_p0() {
        return _p0;
    }

    public void set_p0(Point3D _p0) {
        this._p0 = _p0;
    }

    public Vector get_dir() {
        return _dir;
    }

    public void set_dir(Vector _dir) {
        this._dir = _dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) &&
                _dir.equals(ray._dir);
    }

    @Override
    public String toString() {
        return "" + _p0 + " " + _dir;
    }
}
