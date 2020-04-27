package unittests;


import elements.Camera;
import org.junit.Test;

import primitives.*;
import geometries.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * class of Camera Integration Test
 */
public class CameraIntegrationTest {
    Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
    Camera cam2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));

    /**
     * tests: construct Ray Through Pixel With Sphere
     */

    /**
     * TC01: 2 intersection points
     */
    @Test
   public void constructRayThroughPixelWithSphere1() {
        Sphere sph = new Sphere(1, new Point3D(0, 0, 3));
        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        // checks intersection points with sphere for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = sph.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad" , 2, count);
        System.out.println("count: " + count);

    }

    /**
     * TC02: 18 intersection points
     */
    @Test
    public void constructRayThroughPixelWithSphere2() {
        Sphere sph = new Sphere(2.5, new Point3D(0, 0, 2.5));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with sphere for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = sph.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 18, count);
        System.out.println("count: " + count);
    }

    /**
     * TC03: 10 intersection points
     */
    @Test
    public void constructRayThroughPixelWithSphere3() {
        Sphere sph = new Sphere(2, new Point3D(0, 0, 2));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with sphere for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = sph.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 10, count);
        System.out.println("count: " + count);
    }

    /**
     * TC04: 9 intersection points
     */
    @Test
    public void constructRayThroughPixelWithSphere4() {
        Sphere sph = new Sphere(4, new Point3D(0, 0, -2));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with sphere for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = sph.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 9, count);
        System.out.println("count: " + count);
    }

    /**
     * TC05: 0 intersection points
     */
    @Test
    public void constructRayThroughPixelWithSphere5() {
        Sphere sph = new Sphere(0.5, new Point3D(0, 0, -1));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with sphere for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = sph.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 0, count);
        System.out.println("count: " + count);
    }

    /**
     * tests: construct Ray Through Pixel With Plane
     */

    /**
     * TC06: 9 intersection points
     */
    @Test
    public void constructRayThroughPixelWithPlane1() {
        Plane pla = new Plane(new Point3D(0,0,4),new Vector(0,0,1));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with plane for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = pla.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 9, count);
        System.out.println("count: " + count);
    }


    /**
     * TC07: 9 intersection points
     */
    @Test
    public void constructRayThroughPixelWithPlane2() {
        Plane pla = new Plane(new Point3D(0,0,3),new Vector(0,1,-2));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with plane for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = pla.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 9, count);
        System.out.println("count: " + count);
    }

    /**
     * TC08: 6 intersection points
     */
    @Test
    public void constructRayThroughPixelWithPlane3() {
        Plane pla = new Plane(new Point3D(0,0,4),new Vector(1,1,1));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with plane for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = pla.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 6, count);
        System.out.println("count: " + count);
    }
    /**
     * TC09: 0 intersection points
     */
    @Test
    public void constructRayThroughPixelWithPlane4() {
        Plane pla = new Plane(new Point3D(0,0,4),new Vector(0,1,0));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with plane for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = pla.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 0, count);
        System.out.println("count: " + count);
    }

    /**
     * tests: construct Ray Through Pixel With Triangle
     */

    /**
     * TC10: 1 intersection points
     */
    @Test
    public void constructRayThroughPixelWithTriangle1() {
        Triangle tra = new Triangle(new Point3D(0,-1,2),new Point3D(1,1,2), new Point3D(-1,1,2));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with plane for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = tra.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 1, count);
        System.out.println("count: " + count);
    }

    /**
     * TC11: 2 intersection points
     */
    @Test
    public void constructRayThroughPixelWithTriangle2() {
        Triangle tra = new Triangle(new Point3D(0,-20,2),new Point3D(1,1,2), new Point3D(-1,1,2));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with plane for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = tra.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 2, count);
        System.out.println("count: " + count);
    }

    /**
     * TC12: 0 intersection points
     */
    @Test
    public void constructRayThroughPixelWithTriangle3() {
        Triangle tra = new Triangle(new Point3D(0,-20,2),new Point3D(5,1,2), new Point3D(3,1,2));

        List<Point3D> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        // checks intersection points with plane for each pixel
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                results = tra.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("too bad", 0, count);
        System.out.println("count: " + count);
    }

}
