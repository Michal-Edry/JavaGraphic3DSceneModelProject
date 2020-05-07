package unittests;

import geometries.Intersectable;
import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Sphere class
 */

public class SphereTest {
    /**
     * Test method for {@Link geometries.Sphere#getNormal(geometries.Sphere)}
     */
    @Test
    public void getNormal() {
        Sphere s1 = new Sphere(4, new Point3D(0,0,0));
        Sphere s2 = new Sphere(1, new Point3D(1,1,1));
        // ============ Equivalence Partitions Tests ==============
        assertTrue(s1.getNormal(new Point3D(0,0,4)).equals(new Vector(new Point3D(0,0,1))));
        assertTrue(s1.getNormal(new Point3D(0,0,-4)).equals(new Vector(new Point3D(0,0,-1))));
        assertTrue(s1.getNormal(new Point3D(0,4,0)).equals(new Vector(new Point3D(0,1,0))));
        assertTrue(s1.getNormal(new Point3D(0,-4,0)).equals(new Vector(new Point3D(0,-1,0))));
        assertTrue(s1.getNormal(new Point3D(4,0,0)).equals(new Vector(new Point3D(1,0,0))));
        assertTrue(s1.getNormal(new Point3D(-4,0,0)).equals(new Vector(new Point3D(-1,0,0))));

        assertTrue(s2.getNormal(new Point3D(1,1,0)).equals(new Vector(new Point3D(0,0,-1))));
        assertTrue(s2.getNormal(new Point3D(0,1,1)).equals(new Vector(new Point3D(-1,0,0))));
        assertTrue(s2.getNormal(new Point3D(1,0,1)).equals(new Vector(new Point3D(0,-1,0))));

        // =============== Boundary Values Tests ==================
        try {
            assertTrue(s1.getNormal(new Point3D(0, 0, 0)).equals(new Vector(new Point3D(0, 0, 0))));
            fail("Point can't be center point");
        }
        catch (IllegalArgumentException e){
            assertTrue(e.getMessage()!= null);
        }
    }


    @Test
    public void findIntersections() {

            Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

            // ============ Equivalence Partitions Tests ==============

            // TC01: Ray's line is outside the sphere (0 points)
            assertEquals("Ray's line out of sphere", null,
                    sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

            // TC02: Ray starts before and crosses the sphere (2 points)
            Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
            Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
            List<Intersectable.GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                    new Vector(3, 1, 0)));
            assertEquals("Wrong number of points", 2, result.size());
            if (result.get(0).get_point().get_x().get() > result.get(1).get_point().get_x().get())
                result = List.of(result.get(1), result.get(0));
            assertEquals("Ray crosses sphere", List.of(p1, p2), result);

            // TC03: Ray starts inside the sphere (1 point)
        Ray ray = new Ray(new Point3D(1,0,0.5),new Vector(1,0,0));
        result = sphere.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray starts inside the sphere", new Point3D(1.8660254037844386,0,0.5),result.get(0));

            // TC04: Ray starts after the sphere (0 points)
        ray = new Ray(new Point3D(2,1,1),new Vector(1,0,0));
        assertEquals("Ray starts after the sphere", null,sphere.findIntersections(ray));

            // =============== Boundary Values Tests ==================

            // **** Group: Ray's line crosses the sphere (but not the center)
            // TC11: Ray starts at sphere and goes inside (1 points)
        ray = new Ray(new Point3D(2,0,0),new Vector(-1,-1,-1));
        result = sphere.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray starts at sphere and goes inside",new Point3D(1.333333333333333 ,-0.6666666666666669 ,-0.6666666666666669),result.get(0));

            // TC12: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point3D(2,0,0),new Vector(1,1,1));
        result = sphere.findIntersections(ray);
        assertEquals("Ray starts at sphere and goes outside",null ,result);

        // **** Group: Ray's line goes through the center
            // TC13: Ray starts before the sphere (2 points)
        ray = new Ray(new Point3D(3,0,0),new Vector(-10,0,0));
        result = sphere.findIntersections(ray);
        assertEquals("Wrong number of points", 2, result.size());
        assertEquals("Ray starts before the sphere",new Point3D(2 ,0, 0 ),result.get(0));
        assertEquals("Ray starts before the sphere",new Point3D(0 ,0 ,0),result.get(1));

            // TC14: Ray starts at sphere and goes inside (1 points)
        ray = new Ray(new Point3D(2,0,0),new Vector(-10,0,0));
        result = sphere.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray starts at sphere and goes inside",new Point3D(0 ,0, 0 ),result.get(0));

            // TC15: Ray starts inside (1 points)
        ray = new Ray(new Point3D(1.5,0,0),new Vector(-10,0,0));
        result = sphere.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray starts inside",new Point3D(0 ,0, 0 ),result.get(0));

         // TC16: Ray starts at the center (1 points)
        ray = new Ray(new Point3D(1,0,0),new Vector(1,1,1));
        result = sphere.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray starts at the center",new Point3D(1.5773502691896257 ,0.5773502691896258, 0.5773502691896258),result.get(0));

        // TC17: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point3D(0,0,0),new Vector(-10,0,0));
        result = sphere.findIntersections(ray);
        assertEquals("Ray starts at sphere and goes outside",null,result);

        // TC18: Ray starts after sphere (0 points)
        ray = new Ray(new Point3D(-1,0,0),new Vector(-10,0,0));
        result = sphere.findIntersections(ray);
        assertEquals("Ray starts after sphere",null,result);

            // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
            // TC19: Ray starts before the tangent point
        ray = new Ray(new Point3D(2,0,-1),new Vector(0,0,1));
        result = sphere.findIntersections(ray);
        assertEquals("Ray starts before the tangent point",null,result);

        // TC20: Ray starts at the tangent point
        ray = new Ray(new Point3D(2,0,0),new Vector(0,0,1));
        result = sphere.findIntersections(ray);
        assertEquals("Ray starts at the tangent point",null,result);

        // TC21: Ray starts after the tangent point
        ray = new Ray(new Point3D(2,0,1),new Vector(0,0,1));
        result = sphere.findIntersections(ray);
        assertEquals("Ray starts after the tangent point",null,result);

            // **** Group: Special cases
            // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        //Ray's start point inside sphere
        ray = new Ray(new Point3D(1.5,0,0),new Vector(0,0,1));
        result = sphere.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray's line is outside, ray is orthogonal to ray start to sphere's center line",new Point3D(1.5 , 0 , 0.8660254037844386),result.get(0));

        //Ray's start point on sphere
        ray = new Ray(new Point3D(2,0,0),new Vector(0,0,1));
        result = sphere.findIntersections(ray);
        assertEquals("Ray's line is outside, ray is orthogonal to ray start to sphere's center line",null,result);

        //Ray's start point outside sphere
        ray = new Ray(new Point3D(3,0,0),new Vector(0,0,1));
        result = sphere.findIntersections(ray);
        assertEquals("Ray's line is outside, ray is orthogonal to ray start to sphere's center line",null,result);


    }
}

