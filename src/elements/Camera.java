package elements;

import geometries.Intersectable;
import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

import java.util.Random;


/**
 * class of Camera
 */
public class Camera {
    Point3D _p0;
    Vector _vUp;
    Vector _vTo;
    Vector _vRight;

    private int DOF = 10;
    private double aperture = 4;
    private double _focal = 100;
    Random rand = new Random();


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
     * constructs a ray through a pixel
     * @param nX amount of pixels on X
     * @param nY amount of pixels on y
     * @param j index j
     * @param i index i
     * @param screenDistance screen distance from camera
     * @param screenWidth screen width
     * @param screenHeight screen hight
     * @return Ray
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

    /**
     * constructs a beam of rays that start at the view plane towards the focal point
     * @param nX amount of pixels on X
     * @param nY amount of pixels on y
     * @param j index j
     * @param i index i
     * @param screenDistance screen distance from camera
     * @param screenWidth screen width
     * @param screenHeight screen hight
     * @param focalPlane focal plane
     * @return List of rays
     */
    public List<Ray> constructRaysBeamThroughPixel(int nX, int nY, double j, double i, double screenDistance,double screenWidth, double screenHeight, Plane focalPlane){

        double Rx = screenWidth / nX;//the length of pixel in X axis
        double Ry = screenHeight / nY;//the length of pixel in Y axis

        Point3D Pc = new Point3D(_p0.add(_vTo.scale(screenDistance)));//new point from the camera to the screen

        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);

        //pc is the center of the view plane
        Point3D P = Pc.add(_vRight.scale(xj).subtract(_vUp.scale(yi)));

        Ray ray = new Ray(P, P.subtract(_p0));

        //find focal point
        List<Intersectable.GeoPoint> intersections = focalPlane.findIntersections(ray);
        if(intersections == null){
            throw new IllegalArgumentException("no intersections found");
        }

        Point3D focal_point = intersections.get(0).get_point();

        List<Ray> rays = new LinkedList<>();

        rays.add(ray);


        // x coord middle of pixel/2 downwards
        Point3D tmp = new Point3D(P.get_x().get() - Rx / 2, P.get_y().get(), P.get_z().get());
        rays.add(new Ray(tmp,new Vector(focal_point.subtract(tmp)).normalized()));

        // y coord middle of pixel/2 downward
        tmp = new Point3D(P.get_x().get(), P.get_y().get() - Ry / 2, P.get_z().get());
        rays.add(new Ray(tmp, new Vector(focal_point.subtract(tmp)).normalized()));

        // x coord middle of pixel/2 upwards
        tmp = new Point3D(P.get_x().get() + Rx / 2, P.get_y().get(), P.get_z().get());
        rays.add(new Ray(tmp, new Vector(focal_point.subtract(tmp)).normalized()));

        // y coord middle of pixel/2 upward
        tmp = new Point3D(P.get_x().get(), P.get_y().get() + Ry / 2, P.get_z().get());
        rays.add(new Ray(tmp, new Vector(focal_point.subtract(tmp)).normalized()));

        // x coord middle of pixel/2 downwards  y coord middle of pixel/2 downward
        tmp = new Point3D(P.get_x().get() - Rx / 2, P.get_y().get() - Ry / 2, P.get_z().get());
        rays.add(new Ray(tmp, new Vector(focal_point.subtract(tmp)).normalized()));

        // x coord middle of pixel/2 upwards  y coord middle of pixel/2 downward
        tmp = new Point3D(P.get_x().get() + Rx / 2, P.get_y().get() - Ry / 2, P.get_z().get());
        rays.add(new Ray(tmp, new Vector(focal_point.subtract(tmp)).normalized()));

        // x coord middle of pixel/2 downwards    y coord middle of pixel/2 upward
        tmp = new Point3D(P.get_x().get() - Ry / 2, P.get_y().get() + Ry / 2, P.get_z().get());
        rays.add(new Ray(tmp, new Vector(focal_point.subtract(tmp)).normalized()));

        // x coord middle of pixel/2 upwards   y coord middle of pixel/2 upward
        tmp = new Point3D(P.get_x().get() + Ry / 2, P.get_y().get() + Ry / 2, P.get_z().get());
        rays.add(new Ray(tmp, new Vector(focal_point.subtract(tmp)).normalized()));

        return rays;
    }

    public List<Ray> constructFocalRays(Point3D point)
    {
        Vector v = point.subtract(_p0);
        if (DOF == 0)
            return List.of(new Ray(_p0,v));

        v.normalize();
        Point3D focalPoint = point.add(v.scale(_focal / _vTo.dotProduct(v)));

        List<Ray> rays = new LinkedList<>();
        for(int i = DOF; i < 0; --i){
            double x = rand.nextDouble()*2-1;
            double y = Math.sqrt(1-x*x);
            Point3D p = point;
            double mult = (rand.nextDouble()-0.5)* aperture;
            if(!isZero(x))
                p.add(_vRight.scale(x*mult));
            if(!isZero(y))
                p.add(_vUp.scale(y*mult));
            rays.add(new Ray(p, focalPoint.subtract(p)));
        }
        return rays;
    }
}
