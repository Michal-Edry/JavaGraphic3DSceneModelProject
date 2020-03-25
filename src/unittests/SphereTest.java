package unittests;

import geometries.Plane;
import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

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
        // ============ Equivalence Partitions Tests ==============
        Sphere s = new Sphere(1.0,new Point3D(0.0,0.0,0.0));
        assertEquals("Bad normal to sphere", new Vector(0.0, 0.0, 1.0), s.getNormal(new Point3D(0, 0, 1)));
    }
}