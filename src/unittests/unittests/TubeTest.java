package unittests;

import geometries.Cylinder;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TubeTest {

    @Test
    public void getNormal() {
        Tube t1 = new Tube(1.0,new Ray(new Point3D(1.0,0.0,0.0),new Vector(0.0,1.0,0.0)));
        Vector v = new Vector(new Point3D(1,1,1)).normalize();
        // ============ Equivalence Partitions Tests ==============
        assertTrue(t1.getNormal(new Point3D(2,0,0)).equals(new Vector(new Point3D(1,0,0))));
        assertTrue(t1.getNormal(new Point3D(2,1,1)).equals(v));
    }
}