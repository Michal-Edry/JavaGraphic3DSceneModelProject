package unittests;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Polygon;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
/**
 * Unit tests for geometries.Triangle class
 */
public class TriangleTests {
    /**
     * Test method for {@Link geometries.Triangle#getNormal(geometries.Triangle)}
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tl = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = -Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), tl.getNormal(new Point3D(0, 0, 1)));
    }
    @Test
    public void findIntersections() {

        // ============ Equivalence Partitions Tests ==============
        Triangle triangle = new Triangle(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
        Ray ray;
        List<Intersectable.GeoPoint> result;

        //TC01: Inside triangle (1 point)
        ray = new Ray(new Point3D(-1,-3,-2),new Vector(1,3,2));
        result = triangle.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Inside triangle",new Point3D(0.16666666666666652,0.5,(double)1/3), result.get(0).getPoint());

        //TC02: Outside against edge (0 points)
        Triangle triangle_1 = new Triangle(new Point3D(0,0,0),new Point3D(1,0,0),new Point3D(0,1,0));
        ray = new Ray(new Point3D(-1,-1,-1),new Vector(3,3,1));
        result = triangle_1.findIntersections(ray);
        assertEquals("Outside against edge",null,result);

        //TC03: Outside against vertex (0 points)
        ray = new Ray(new Point3D(-1,-1,-1),new Vector(-3,-3,1));
        result = triangle_1.findIntersections(ray);
        assertEquals("Outside against vertex",null,result);

        // =============== Boundary Values Tests ==================

        //TC04: On edge (0 points)
        ray = new Ray(new Point3D(-1,-1,-1),new Vector(3,3,2));
        result = triangle.findIntersections(ray);
        assertEquals("On edge",null,result);


        //TC05: In vertex (0 points)
        ray = new Ray(new Point3D(-1,-1,-1),new Vector(1,1,2));
        result = triangle.findIntersections(ray);
        assertEquals("In vertex",null,result);

        //TC06: On edge's continuation (0 points)
        ray = new Ray(new Point3D(-1,-1,-1),new Vector(3,0,1));
        result = triangle.findIntersections(ray);
        assertEquals("On edge's continuation",null,result);

    }

}

