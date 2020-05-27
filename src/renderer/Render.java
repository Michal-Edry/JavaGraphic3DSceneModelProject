package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.*;
import primitives.*;
import scene.Scene;
import java.awt.Color;
import geometries.Intersectable.GeoPoint;


import java.util.List;

import static primitives.Util.alignZero;

/**
 * class of Render
 */
public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * constructor
     * @param imageWriter ImageWriter
     * @param scene Scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene=   scene;
    }

    /**
     * Filling the buffer according to the geometries that are in the scene.
     * This function is not creating the picture file, but create the buffer pf pixels
     */
    public void renderImage() {
        java.awt.Color background = _scene.getBackground().getColor();
        Camera camera= _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        double  distance = _scene.getDistance();

        //width and height are the number of pixels in the rows
        //and columns of the view plane
        int width = (int) _imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();

        //Nx and Ny are the width and height of the image.
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        Ray ray;
        for (int row = 0; row < Ny; row++) {
            for (int column = 0; column < Nx; column++) {
                ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
                List<GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(ray);
                if (intersectionPoints == null) {
                    _imageWriter.writePixel(column, row, background);
                } else {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(column, row, calcColor(closestPoint,ray).getColor());
                }
            }
        }
    }


    /**
     * checks a sign of a value
     * @param val double
     * @return boolean
     */
    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param v          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * @author Dan Zilberstein
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private primitives.Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, primitives.Color ip) {
        double p = nShininess;

        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return primitives.Color.BLACK; // view from direction opposite to r vector
        }
        return ip.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component coef
     * @param nl dot-product n*l
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein
     * <p>
     * The diffuse component is that dot product n•L that we discussed in class. It approximates light, originally
     * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * surface is paper. In general, you'll also want this to have a non-gray color value, so this term would in general
     * be a color defined as: [rd,gd,bd](n•L)
     */
    private primitives.Color calcDiffusive(double kd, double nl, primitives.Color ip) {
        if (nl < 0) nl = -nl;
        return ip.scale(nl * kd);
    }

    /** calculates Reflection Ray
     * @param pointGeo  Point3D
     * @param inRay Ray
     * @param n Vector
     * @return Ray
     */
    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) {
        //𝒓=𝒗−𝟐∙𝒗∙𝒏∙𝒏
        Vector v = inRay.get_dir();
        double vn = v.dotProduct(n);
        if (vn == 0) {
            return null;
        }
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }

    /**
     * calculates Refraction ray
     * @param pointGeo Point3D
     * @param inRay Ray
     * @param n Vector
     * @return Ray
     */
    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.get_dir(), n);
    }

    /**
     * Finding the closest point to the P0 of the camera.
     * @param  intersectionPoints list of points, the function should find from
     * this list the closet point to P0 of the camera in the scene.
     * @return  the closest point to the camera
     * change to public for test
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
        GeoPoint result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.getCamera().get_p0();

        for (GeoPoint geo: intersectionPoints ) {
            Point3D pt = geo.get_point();
            double distance = p0.distance(pt);
            if (distance < mindist){
                mindist= distance;
                result = geo;
            }
        }
        return  result;
    }

    /**
     * Printing the grid with a fixed interval between lines
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval, Color colorsep) {
        double rows = this._imageWriter.getNx();
        double collumns = _imageWriter.getNy();
        //Writing the lines.
        for (int col = 0; col < collumns; col++) {
            for (int row = 0; row < rows; row++) {
                if (col % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(row, col, colorsep);
                }
            }
        }
    }

    /**
     * writes to image
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * checks if a point is unshaded
     * @param l Vector
     * @param n Vector
     * @param geopoint GeoPoint
     * @return boolean
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint)
    {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geopoint.get_point(), lightDirection, n);

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint.get_point());
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.get_point().distance(geopoint.get_point()) - lightDistance) <= 0 && gp.get_geometry().get_material().get_kT() == 0)
                return false;
        }
        return true;
    }

    /**
     * calculates the closest point to the rays head
     * @param ray Ray
     * @return GeoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray){
        if (ray == null) {
            return null;
        }
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.get_p0();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.get_point());
            if (distance < closestDistance) {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }

    /**
     * calls the recursive func to calculates a color in a point
     * @param geoPoint GeoPoint
     * @param inRay Ray
     * @return Color
     */
    private primitives.Color calcColor(GeoPoint geoPoint, Ray inRay) {
        primitives.Color color = calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
        color = color.add(_scene.getAmbientLight().get_intensity());
        return color;
    }

    /**
     * recursive func: calculates the color in a point
     * @param geoPoint GeoPoint
     * @param inRay Ray
     * @param level int
     * @param k double
     * @return Color
     */
    private primitives.Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
        if (level == 1 || k < MIN_CALC_COLOR_K) {
            return primitives.Color.BLACK;
        }

        primitives.Color result = geoPoint.get_geometry().get_emission();
        Point3D pointGeo = geoPoint.get_point();

        Vector v = pointGeo.subtract(_scene.getCamera().get_p0()).normalize();
        Vector n = geoPoint.get_geometry().getNormal(pointGeo);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            //ray parallel to geometry surface ??
            //and orthogonal to normal
            return result;
        }

        Material material = geoPoint.get_geometry().get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        double kr = geoPoint.get_geometry().get_material().get_kR();
        double kt = geoPoint.get_geometry().get_material().get_kT();
        double kkr = k * kr;
        double kkt = k * kt;

        result = result.add(getLightSourcesColors(geoPoint, k, result, v, n, nv, nShininess, kd, ks));

        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(pointGeo, inRay, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                result = result.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(pointGeo, inRay, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null) {
                result = result.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }
        return result;
    }

    /**
     * calculates light sources colors
     * @param geoPoint GeoPoint
     * @param k double
     * @param result Color
     * @param v Vector
     * @param n Vector
     * @param nv double
     * @param nShininess int
     * @param kd double
     * @param ks double
     * @return Color
     */
    private primitives.Color getLightSourcesColors(GeoPoint geoPoint, double k, primitives.Color result, Vector v, Vector n, double nv, int nShininess, double kd, double ks) {
        Point3D pointGeo = geoPoint.get_point();
        List<LightSource> lightSources = _scene.getLightSources();
        if (lightSources != null) {
            for (LightSource lightSource : lightSources) {
                Vector l = lightSource.getL(pointGeo);
                double nl = alignZero(n.dotProduct(l));
                if (nl * nv > 0) {
//                if (sign(nl) == sign(nv) && nl != 0 && nv != 0) {
//                    if (unshaded(lightSource, l, n, geoPoint)) {
                    double ktr = transparency(lightSource, l, n, geoPoint);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        primitives.Color ip = lightSource.getIntensity(pointGeo).scale(ktr);
                        result = result.add(
                                calcDiffusive(kd, nl, ip),
                                calcSpecular(ks, l, n, nl, v, nShininess, ip));
                    }
                }
            }
        }
        return result;
    }

    /**
     * checks unshaded func
     * @param light LightSource
     * @param l Vector
     * @param n Vector
     * @param geopoint GeoPiont
     * @return double
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.get_point(), lightDirection, n);
        Point3D pointGeo = geopoint.get_point();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return 1d;
        }
        double lightDistance = light.getDistance(pointGeo);
        double ktr = 1d;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.get_point().distance(pointGeo) - lightDistance) <= 0) {
                ktr *= gp.get_geometry().get_material().get_kT();
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }
        }
        return ktr;
    }
}
