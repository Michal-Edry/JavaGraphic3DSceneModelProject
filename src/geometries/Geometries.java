package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * class of Geometries
 */
public class Geometries implements Intersectable {
    private List<Intersectable> _geometries;

    /**
     * default constructor
     */
    public Geometries() {
        _geometries = new ArrayList<>();
    }

    /**
     * constructor
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
        this._geometries = new ArrayList<>();
        add(geometries);
    }

    /**
     * adds geometries to the list
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
            _geometries.add(geo);
        }
    }

    /**
     * gets a Ray and returns all the intersection points with the geometries.
     * @param ray
     * @return List of Point3D
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = null;

        for (Intersectable geo : _geometries) {
            List<GeoPoint> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new ArrayList<GeoPoint>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }
}
