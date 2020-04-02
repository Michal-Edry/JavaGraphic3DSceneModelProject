package unittests;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
/**
 * Unit tests for primitives.Vector class
 */
public class VectorTest {

    Vector v1 = new Vector(1.0, 1.0, 1.0);
    Vector v2 = new Vector(-1.0, -1.0, -2.0);

    /**
     * Test method for {@Link primitives.Vector#subtract(primitives.Vector)}
     */
    @Test
    public void subtract() {
        // ============ Equivalence Partitions Tests ==============
        v1 = v1.subtract(v2);
        assertTrue(v1.equals(new Vector(2.0,2.0,3.0)));

        v2 = v2.subtract(v1);
        assertTrue(v2.equals(new Vector(-3.0,-3.0,-5.0)));

        // =============== Boundary Values Tests ==================
        try {
            v1 = v1.subtract(v1);
            fail("Vector (0,0,0) not valid");
        }
        catch (IllegalArgumentException e){
            assertTrue(e.getMessage()!= null);
        }

    }

    /**
     * Test method for {@Link primitives.Vector#add(primitives.Vector)}
     */
    @Test
    public void add() {
        // ============ Equivalence Partitions Tests ==============
        v1 = v1.add(v2);
        assertTrue(v1.equals(new Vector(0.0,0.0,-1.0)) );

        v2 = v2.add(v1);
        assertTrue(v2.equals(new Vector(-1.0,-1.0,-3.0)) );

        // =============== Boundary Values Tests ==================
        try{
            Vector v3 = new Vector(0.0,0.0,1.0);
            v1 = v1.add(v3);
            fail("Vector (0,0,0) not valid");
        }
        catch (IllegalArgumentException e){
            assertTrue(e.getMessage()!= null);
        }
    }

    /**
     * Test method for {@Link primitives.Vector#scale(primitives.Vector)}
     */
    @Test
    public void scale() {
        // ============ Equivalence Partitions Tests ==============
        v1 = v1.scale(1);
        assertTrue(v1.equals(v1) );

        v1 = v1.scale(2);
        assertTrue(v1.equals(new Vector(2.0,2.0,2.0)));

        v1 = v1.scale(-2);
        assertTrue(v1.equals(new Vector(-4.0,-4.0,-4.0)));

        // =============== Boundary Values Tests ==================
        try{
            v1 = v1.scale(0.0);
            fail("Vector (0,0,0) not valid");
        }
        catch (IllegalArgumentException e){
            assertTrue(e.getMessage()!= null);
        }
    }

    /**
     * Test method for {@Link primitives.Vector#dotProduct(primitives.Vector)}
     */
    @Test
    public void dotProduct(){
        // ============ Equivalence Partitions Tests ==============
        assertTrue((v1.dotProduct(v2).equals(-1.0 + -1.0 + -2.0)));

        // =============== Boundary Values Tests ==================
            Vector v3 = new Vector(new Point3D(1.0,1.0,-1.0));
            assertTrue(v2.dotProduct(v3).equals(-1.0 + -1.0 +2.0));
    }

    /**
     * Test method for {@Link primitives.Vector#crossProduct(primitives.Vector)}
     */
    @Test
    public void crossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v3 = v1.crossProduct(v2);

        assertEquals("", 0, v3.dotProduct(v2), 1e-10);
        assertEquals("", 0, v3.dotProduct(v1), 1e-10);

        Vector v4 = v2.crossProduct(v1);
        Point3D p = v3.get_head().add(v4);
        assertEquals("", 0, p.distance(Point3D.ZERO), 1e-10);

        // =============== Boundary Values Tests ==================
        try{
            v3 = v3.add(v4);
            fail("Vector (0,0,0) not valid");
        }
        catch (IllegalArgumentException e){
            assertTrue(e.getMessage()!= null);
        }
    }

    /**
     * Test method for {@Link primitives.Vector#length(primitives.Vector)}
     */
    @Test
    public void length() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.length() == Math.sqrt(1.0 + 1.0 + 1.0));
        assertTrue(v2.length() == Math.sqrt(1.0 + 1.0 + 4.0));

    }

    /**
     * Test method for {@Link primitives.Vector#normalize(primitives.Vector)}
     */
    @Test
    public void normalize() {
        // ============ Equivalence Partitions Tests ==============
        v1= v1.normalize();
        assertEquals("", 1, v1.length(),1e-10);
        v2 = v2.normalize();
        assertEquals("", 1, v2.length(),1e-10);
    }

    @Test
    public void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.LengthSquared() == (1.0 + 1.0 + 1.0));
        assertTrue(v2.LengthSquared() == (1.0 + 1.0 + 4.0));
    }

    @Test
    public void normalized() {
        // ============ Equivalence Partitions Tests ==============
        Vector v3 = v1.normalized();
        assertEquals("", 1, v3.length(),1e-10);
        assertEquals("", Math.sqrt(3), v1.length(),1e-10);
    }
}