package unittests;

import geometries.Polygon;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
/**
 * Testing Polygons
 *
 */
public class PolygonTests {
    /**
     * Test method for
     * {@link geometries.Polygon
     * #Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertix on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }
    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = -Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }


    @Test
    public void findIntersections() {

        // ============ Equivalence Partitions Tests ==============

        Polygon polygon = new Polygon(new Point3D(0,0,0), new Point3D(1,0,0), new Point3D(1,1,1), new Point3D(0,1,1));
        Ray ray;
        List<Point3D> result;

        //TC01: Inside polygon (1 point)
        ray = new Ray(new Point3D(0.5,0.5,-1),new Vector(new Point3D(0,0,1)));
        result = polygon.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Inside polygon",new Point3D(0.5,0.5,0.5), result.get(0));

        //TC02: Outside against edge (0 points)
        ray = new Ray(new Point3D(2,0.5,-1), new Vector(new Point3D(0,0,1)));
        result = polygon.findIntersections(ray);
        assertEquals("Outside against edge",null,result);

        //TC03: Outside against vertex (0 points)
        ray = new Ray(new Point3D(2,3,-1), new Vector(new Point3D(0,0,1)));
        result = polygon.findIntersections(ray);
        assertEquals("Outside against vertex",null,result);

        // =============== Boundary Values Tests ==================

        //TC04: On edge (0 points)
        ray = new Ray(new Point3D(1,0.5,-1), new Vector(new Point3D(0,0,1)));
        result = polygon.findIntersections(ray);
        assertEquals("On edge",null,result);

        //TC05: In vertex (0 points)
        ray = new Ray(new Point3D(1,0,-1), new Vector(new Point3D(0,0,1)));
        result = polygon.findIntersections(ray);
        assertEquals("In vertex",null,result);

        //TC06: On edge's continuation (0 points)
        ray = new Ray(new Point3D(2,2,-1), new Vector(new Point3D(0,0,1)));
        result = polygon.findIntersections(ray);
        assertEquals("On edge's continuation",null,result);


    }
}