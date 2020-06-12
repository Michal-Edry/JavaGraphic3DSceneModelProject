package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.List;
import java.util.Objects;

/**
 * interface of Intersectable
 */
public interface Intersectable {
    List<GeoPoint> findIntersections(Ray ray);

    /**
     * inner class of GeoPoint
     */
    public  class GeoPoint {
        Geometry _geometry;
        Point3D _point;

        /**
         * constructor
         * @param geometry Geometry
         * @param point Point3D
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this._geometry = geometry;
            this._point = point;
        }

        /**
         * checks if 2 geometries equal
         * @param o gets a geometry
         * @return if they are equal
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(_geometry, geoPoint._geometry) &&
                    Objects.equals(_point, geoPoint._point);
        }

        /**
         * getter for _point
         * @return Point3D
         */
        public Point3D getPoint() {
            return _point;
        }

        /**
         * getter for _geometry
         * @return Geometry
         */
        public Geometry getGeometry() {
            return _geometry;
        }
    }


}
