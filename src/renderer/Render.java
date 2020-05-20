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

    private static final double DELTA = 0.1;

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
                    _imageWriter.writePixel(column, row, calcColor(closestPoint));
                }
            }
        }
    }

    /**
     * calculates the color in a Point
     * @param geoPoint Point3D
     * @return Color
     */
    private Color calcColor(GeoPoint geoPoint) {
        primitives.Color resultColor = new primitives.Color(_scene.getAmbientLight().get_intensity());
        resultColor = resultColor.add(geoPoint.get_geometry().get_emission());


        List<LightSource> lights = _scene.getLightSources();

        Vector v = geoPoint.get_point().subtract(_scene.getCamera().get_p0()).normalize();
        Vector n = geoPoint.get_geometry().getNormal(geoPoint.get_point());

        Material material = geoPoint.get_geometry().get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        if (_scene.getLightSources() != null) {
            for (LightSource lightSource : lights) {

                Vector l = lightSource.getL(geoPoint.get_point());
                double nl = alignZero(n.dotProduct(l));
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) {
                    if (unshaded(lightSource, l, n, geoPoint)) {
                        primitives.Color ip = lightSource.getIntensity(geoPoint.get_point());
                        resultColor = resultColor.add(
                                calcDiffusive(kd, nl, ip),
                                calcSpecular(ks, l, n, nl, v, nShininess, ip)
                        );
                    }
                }
            }
        }

        return resultColor.getColor();
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

        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
        Point3D point = geopoint.get_point().add(delta);
        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint.get_point());
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.get_point().distance(geopoint.get_point()) - lightDistance) <= 0)
                return false;
        }
        return true;
    }
}
