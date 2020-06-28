package elements;

import geometries.*;
import primitives.*;


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

    private int DOF = 0;
    private static Random rand = new Random();
    private double aperture = 5d;
    private int numOfRays = 20;
    private double focalDistance = 100;

    /**
     * getter for aperture
     * @return double
     */
    public double getAperture() {
        return aperture;
    }

    /**
     * setter for aperture
     * @param aperture double
     * @return this camera
     */
    public Camera setAperture(double aperture) {
        this.aperture = aperture;
        return this;
    }

    /**
     * getter for numOfRays
     * @return int
     */
    public int getNumOfRays() {
        return numOfRays;
    }

    /**
     * setter for numOfRays
     * @param numOfRays int
     * @return this camera
     */
    public Camera setNumOfRays(int numOfRays) {
        this.numOfRays = numOfRays;
        return this;
    }

    /**
     * getter for focalDistance
     * @return double
     */
    public double getFocalDistance() {
        return focalDistance;
    }

    /**
     * setter for focalDistance
     * @param focalDistance double
     * @return this camera
     */
    public Camera setFocalDistance(double focalDistance) {
        this.focalDistance = focalDistance;
        return this;
    }

    /**
     * getter for DOF
     * @return int
     */
    public int getDOF() {
        return DOF;
    }

    /**
     * setter for DOF
     * @param DOF int
     * @return this camera
     */
    public Camera setDOF(int DOF) {
        this.DOF = DOF;
        return this;
    }

    /**
     * constructor
     *
     * @param _p0  Point 3D
     * @param _vUp Vector
     * @param _vTo Vector
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {
        if (_vUp.dotProduct(_vTo) != 0)
            throw new IllegalArgumentException("Vup must be orthogonal to Vto");
        this._p0 = _p0;
        this._vUp = _vUp.normalize();
        this._vTo = _vTo.normalize();
        this._vRight = _vTo.crossProduct(_vUp).normalize();
    }

    /**
     * getter for p0
     *
     * @return point3D p0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getter for Vup
     *
     * @return Vector Vup
     */
    public Vector getVUp() {
        return _vUp;
    }

    /**
     * getter for Vto
     *
     * @return Vector Vto
     */
    public Vector getVTo() {
        return _vTo;
    }

    /**
     * getter for Vright
     *
     * @return Vector Vright
     */
    public Vector getVRight() {
        return _vRight;
    }

    /**
     * constructs a ray through a pixel
     *
     * @param nX             amount of pixels on X
     * @param nY             amount of pixels on y
     * @param j              index j
     * @param i              index i
     * @param screenDistance screen distance from camera
     * @param screenWidth    screen width
     * @param screenHeight   screen hight
     * @return Ray
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) {
        if (isZero(screenDistance)) {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        Point3D Pc = _p0.add(_vTo.scale(screenDistance));

        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;

        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);

        Point3D Pij = Pc;

        if (!isZero(xj)) {
            Pij = Pij.add(_vRight.scale(xj));
        }
        if (!isZero(yi)) {
            Pij = Pij.subtract(_vUp.scale(yi)); // Pij.add(_vUp.scale(-yi))
        }

        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0, Vij);
    }


    /**
     * constructs a beam of rays that start at the view plane towards the focal point
     * @param nX amount of pixels on X
     * @param nY amount of pixels on y
     * @param j index j
     * @param i index i
     * @param screenDistance screen distance from camera
     * @param screenWidth screen width
     * @param screenHeight screen height
     * @return List of Rays
     */
    public List<Ray> constructRayBeam(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) {
        List<Ray> result = new LinkedList<>();

        Point3D Pc = _p0.add(_vTo.scale(screenDistance));

        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;

        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);

        Point3D pij = Pc;

        if (!isZero(xj)) {
            pij = pij.add(_vRight.scale(xj));
        }
        if (!isZero(yi)) {
            pij = pij.subtract(_vUp.scale(yi)); // Pij.add(_vUp.scale(-yi))
        }


        Vector vToToFocal = pij.subtract(_p0).normalize();
        if (numOfRays == 1 || isZero(aperture)) {
            result.add(new Ray(_p0, vToToFocal));
            return result;
        }

        Ray centerRay = new Ray(pij, vToToFocal);
        result.add(centerRay);
        Point3D focalPoint = pij.add(vToToFocal.scale(focalDistance / _vTo.dotProduct(vToToFocal)));

        for (int k = 1; k < numOfRays; k++) {
            Point3D randPoint = new Point3D(pij);
            double x = rand.nextDouble() * 2 - 1;
            double y = Math.sqrt(1 - x * x);
            double mult = (rand.nextDouble()-0.5)*aperture;
            if (!isZero(x))
                randPoint = randPoint.add(_vRight.scale(x * mult));
            if (!isZero(y))
                randPoint = randPoint.add(_vUp.scale(y * mult));
            result.add(new Ray(randPoint, focalPoint.subtract(randPoint).normalized()));
        }
        return result;
    }

    /**
     * calculates the center ray and 4 corner rays of the aperture and the sub pixel
     * @param nX amount of pixels on X
     * @param nY amount of pixels on y
     * @param j index j
     * @param i index i
     * @param screenDistance screen distance from camera
     * @param screenWidth screen width
     * @param screenHeight screen height
     * @param reduce reduce the pixel into a sub pixel
     * @param center center of sub pixel
     * @return List of Rays
     */
    public List<Ray> constructAdaptiveRayBeamThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight, double reduce, Point3D center){
        List<Ray> result = new LinkedList<>();

        Point3D Pc = _p0.add(_vTo.scale(screenDistance)); //center point

        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;

        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);

        Point3D pij = Pc;

        if (!isZero(xj)) {
            pij = pij.add(_vRight.scale(xj));
        }
        if (!isZero(yi)) {
            pij = pij.subtract(_vUp.scale(yi)); // Pij.add(_vUp.scale(-yi))
        }

        Vector vToToFocal = pij.subtract(_p0).normalize();


        Point3D middle = pij;
        if(!center.equals(Point3D.ZERO)){ //if center is sub pixel use it as the center
            middle = center;
        }

        Point3D focalPoint = pij.add(vToToFocal.scale(focalDistance / _vTo.dotProduct(vToToFocal)));

        //result.add(new Ray(middle, focalPoint.subtract(middle)));// adds center ray
        result.add(new Ray(middle, vToToFocal));// adds center ray

        if (numOfRays == 1 || isZero(aperture)) {
            return result;
        }

        double move = aperture/reduce; // the amount we need to move around the center point to get to the corners

        //corner #1
        Point3D temp = new Point3D(middle.getX().get() - move, middle.getY().get() - move, middle.getZ().get());
        result.add(new Ray(temp,(focalPoint.subtract(temp)).normalized()));

        //corner #2
        temp = new Point3D(middle.getX().get() - move, middle.getY().get() + move, middle.getZ().get());
        result.add(new Ray(temp,focalPoint.subtract(temp).normalized()));

        //corner #3
        temp = new Point3D(middle.getX().get() + move, middle.getY().get() - move, middle.getZ().get());
        result.add(new Ray(temp,new Vector(focalPoint.subtract(temp)).normalized()));

        //corner #4
        temp = new Point3D(middle.getX().get() + move, middle.getY().get() + move, middle.getZ().get());
        result.add(new Ray(temp,new Vector(focalPoint.subtract(temp)).normalized()));

        return result;
    }
}