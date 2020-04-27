package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * class of Camera
 */
public class Camera {
    Point3D _p0;
    Vector _vUp;
    Vector _vTo;
    Vector _vRight;

    /**
     * constructor
     * @param _p0 Point 3D
     * @param _vUp Vector
     * @param _vTo Vector
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {
        if(_vUp.dotProduct(_vTo) != 0)
            throw new IllegalArgumentException("Vup must be orthogonal to Vto");
        this._p0 = _p0;
        this._vUp = _vUp.normalize();
        this._vTo = _vTo.normalize();
        this._vRight = _vTo.crossProduct(_vUp).normalize();
    }

    /**
     * getter for p0
     * @return point3D p0
     */
    public Point3D get_p0() {
        return _p0;
    }

    /**
     * getter for Vup
     * @return Vector Vup
     */
    public Vector get_vUp() {
        return _vUp;
    }

    /**
     * getter for Vto
     * @return Vector Vto
     */
    public Vector get_vTo() {
        return _vTo;
    }
    /**
     * getter for Vright
     * @return Vector Vright
     */
    public Vector get_vRight() {
        return _vRight;
    }

    /**
     *
     * @param nX
     * @param nY
     * @param j index j
     * @param i index i
     * @param screenDistance screen distance from camera
     * @param screenWidth screen width
     * @param screenHeight screen hight
     * @return
     */
    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight)
    {
        if (isZero(screenDistance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        Point3D Pc = _p0.add(_vTo.scale(screenDistance));

        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;

        double yi =  ((i - nY/2d)*Ry + Ry/2d);
        double xj=   ((j - nX/2d)*Rx + Rx/2d);

        Point3D Pij = Pc;

        if (! isZero(xj))
        {
            Pij = Pij.add(_vRight.scale(xj));
        }
        if (! isZero(yi))
        {
            Pij = Pij.subtract(_vUp.scale(yi)); // Pij.add(_vUp.scale(-yi))
        }

        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0,Vij);
    }

}
