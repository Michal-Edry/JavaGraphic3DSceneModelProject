package renderer;

import elements.Camera;
import geometries.*;
import primitives.*;
import scene.Scene;
import java.awt.Color;
import geometries.Intersectable.GeoPoint;


import java.util.List;

/**
 * class of Render
 */
public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;

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
                    _imageWriter.writePixel(column-1, row-1, calcColor(closestPoint));
                }
            }
        }
    }

    /**
     * calculates the color in a Point
     * @param point Point3D
     * @return Color
     */
    private Color calcColor(GeoPoint point) {
        primitives.Color resultColor;
        primitives.Color ambientLight = new primitives.Color(_scene.getAmbientLight().getIntensity());
        primitives.Color emissionLight = point.get_geometry().get_emission();

        resultColor = ambientLight;
        resultColor = resultColor.add(emissionLight);

        return resultColor.getColor();

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

}
