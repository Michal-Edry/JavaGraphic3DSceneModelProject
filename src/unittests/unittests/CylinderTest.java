package unittests;

import geometries.Cylinder;
import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
/**
 * Unit tests for geometries.Cylinder class
 */

public class CylinderTest {

    /**
     * Test method for {@Link geometries.Cylinder#getNormal(geometries.Cylinder)}
     */
    @Test
    public void getNormal() {
        Cylinder c1 = new Cylinder(2.0,new Ray(new Point3D(0.0,0.0,0.0),new Vector(0.0,0.0,1.0)),6.0);
        // ============ Equivalence Partitions Tests ==============
        //point is on the side
        assertTrue(c1.getNormal(new Point3D(2,0,2)).equals(new Vector(new Point3D(1,0,0))));
        //point on the base with center point
        assertTrue(c1.getNormal(new Point3D(1,0,0)).equals(new Vector(new Point3D(0,0,-1))));
        //point on the base with out center point
        assertTrue(c1.getNormal(new Point3D(1,0,6)).equals(new Vector(new Point3D(0,0,1))));

        // =============== Boundary Values Tests ==================
        // was checked in EP tests
    }
}