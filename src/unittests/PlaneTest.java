package unittests;

import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

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
}
