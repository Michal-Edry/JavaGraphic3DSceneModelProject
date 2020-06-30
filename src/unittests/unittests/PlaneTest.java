package unittests;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;
/**
 * Unit tests for geometries.Plane class
 */

public class PlaneTest {
    /**
     * Test method for {@Link geometries.Plane#getNormal(geometries.Plane)}
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = -Math.sqrt(1d / 3);
        assertEquals("Bad normal to plane", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    @Test
    public void findIntersections() {

        // ============ Equivalence Partitions Tests ==============

        Plane plane = new Plane(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
        Ray ray;
        List<Intersectable.GeoPoint> result;
        //TC01: Ray intersects the plane (1 point)
        ray = new Ray(new Point3D(-1,-1,-1),new Vector(1,1,2));
        result = plane.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray intersects the plane", new Point3D(0,0,1),result.get(0).getPoint());

        ray = new Ray(new Point3D(-1,-1,-1),new Vector(1,3,2));
        result = plane.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray intersects the plane", new Point3D((double)-1/3,1,(double)1/3),result.get(0).getPoint());

        //TC02: Ray does not intersect the plane (0 points)
        ray = new Ray(new Point3D(1,1,1),new Vector(1,3,2));
        result = plane.findIntersections(ray);
        assertEquals("Ray does not intersect the plane", null ,result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane (0 points)
            //TC03: Ray is included in the plane
        ray = new Ray(new Point3D(1,0,0),new Vector(1,-1,0));
        result = plane.findIntersections(ray);
        assertEquals("Ray is included in the plane", null ,result);

            //TC04: Ray is not included in the plane
        ray = new Ray(new Point3D(2,0,0),new Vector(new Point3D(1,-1,0)));
        result = plane.findIntersections(ray);
        assertEquals("Ray is not included in the plane", null ,result);

        // **** Group: Ray is orthogonal to the plane
            //TC05: Ray starts before the plane (1 point)
        ray = new Ray(new Point3D(-1,-1,-1),new Vector(1,1,1));
        result = plane.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray starts before the plane",new Point3D((double)1/3,(double)1/3,(double)1/3),result.get(0).getPoint());

            //TC06: Ray starts in the plane (0 points)
        ray = new Ray(new Point3D((double)1/3,(double)1/3,(double)1/3),new Vector(new Point3D(1,1,1)));
        result = plane.findIntersections(ray);
        assertEquals("Ray starts in the plane",null,result);

            //TC07: Ray starts after the plane (0 points)
        ray = new Ray(new Point3D(1,1,1),new Vector(1,1,1));
        result = plane.findIntersections(ray);
        assertEquals("Ray starts in the plane",null,result);


        //TC08: Ray is neither orthogonal nor parallel to and begins at the plane (0 points)
        ray = new Ray(new Point3D((double)1/3,(double)1/3,(double)1/3),new Vector(new Point3D(1,3,2)));
        result = plane.findIntersections(ray);
        assertEquals("Ray starts in the plane",null,result);

        //TC09: Ray is neither orthogonal nor parallel to the plane and begins in, the same point which appears as reference point in the plane. (0 points)
        ray = new Ray(new Point3D(1,0,0),new Vector(1,3,2));
        result = plane.findIntersections(ray);
        assertEquals("Ray starts in the plane",null,result);

    }
}
