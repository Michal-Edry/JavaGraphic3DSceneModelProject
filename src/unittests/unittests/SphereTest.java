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
}

