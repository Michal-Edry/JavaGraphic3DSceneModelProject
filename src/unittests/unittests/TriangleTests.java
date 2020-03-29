package unittests;

import geometries.Polygon;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

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
}

