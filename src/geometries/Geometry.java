package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface of geometries
 */
public interface Geometry extends Intersectable {
    public Vector getNormal(Point3D _p);
}
