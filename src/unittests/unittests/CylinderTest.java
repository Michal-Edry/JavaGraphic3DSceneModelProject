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
        Cylinder c1 = new Cylinder(1.0,new Ray(new Point3D(1.0,0.0,0.0),new Vector(0.0,1.0,0.0)),1.0);
        //Cylinder c2 = new Cylinder(1.0,new Ray(new Point3D(1.0,0.0,0.0),new Vector(1.0,0.0,0.0)),2.0);
        // ============ Equivalence Partitions Tests ==============
        assertTrue(c1.getNormal(new Point3D(1,0,0)).equals(new Vector(new Point3D(0,1,0))));
        assertTrue(c1.getNormal(new Point3D(2,0,0)).equals(new Vector(new Point3D(0,1,0))));
        // =============== Boundary Values Tests ==================

    }
}