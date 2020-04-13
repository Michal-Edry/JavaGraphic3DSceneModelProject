package unittests;

import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class GeometriesTest {

    @Test
    public void findIntersections() {
        Ray ray;
        List<Point3D> result;
        // ============ Equivalence Partitions Tests ==============
        Geometries geometries = new Geometries(new Sphere(2, new Point3D(0,0,0)),
                                new Triangle(new Point3D(1,0,0),new Point3D(5,0,0),new Point3D(0,4,5)),
                                new Plane(new Point3D(7,0,0), new Point3D(7,0,7),new Point3D(7,7,7)),
                                new Polygon(new Point3D(9,0,0), new Point3D(9,9,0), new Point3D(9,9,9), new Point3D(9,0,9)));

        //TC01: 2 - sphere, 1 - triangle
        ray= new Ray(new Point3D(1,1.5,-4), new Vector(0,0,1));
        result = geometries.findIntersections(ray);
        assertEquals("Wrong number of points", 3, result.size());

        //Tc02: 2 - sphere, 1 - plane, 1 - polygon
        ray= new Ray(new Point3D(10,1,1), new Vector(-1,0,0));
        result = geometries.findIntersections(ray);
        assertEquals("Wrong number of points", 4, result.size());

        // =============== Boundary Values Tests ==================

        //TC03: no geometries
        Geometries geometries1 = new Geometries();
        result = geometries1.findIntersections(ray);
        assertEquals("no geometries",null,result);

        //TC04: no intersection points
        ray = new Ray(new Point3D(11,0,0), new Vector(1,1,1));
        result = geometries.findIntersections(ray);
        assertEquals("no intersection points",null,result);

        //TC05: intersection with one geometry
        ray = new Ray(new Point3D(8,0,0),new Vector(1,1,1));
        result = geometries.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());

        //TC06: intersection with all geometries
        Geometries geometries2 = new Geometries(new Sphere(2, new Point3D(0,0,0)),
                new Triangle(new Point3D(1,0,0),new Point3D(5,6,0),new Point3D(0,4,5)),
                new Plane(new Point3D(7,0,0), new Point3D(7,0,7),new Point3D(7,7,7)),
                new Polygon(new Point3D(9,0,0), new Point3D(9,9,0), new Point3D(9,9,9), new Point3D(9,0,9)));

        ray = new Ray(new Point3D(-3,1,-1),new Vector(1,0.2,0.5));
        result = geometries2.findIntersections(ray);
        assertEquals("Wrong number of points", 5, result.size());

    }

}