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

    private int _depthOfField;

    private int _adaptiveSampling = 0;

    private int _threads = 1;
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean _print = false; // printing progress percentage



    /**
     * constructor
     * @param imageWriter ImageWriter
     * @param scene       Scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
        _depthOfField = _scene.getCamera().getDOF(); //if 0: don't use depth of fields, else: use
    }

    /**
     * setter of _depthOfField
     * @param _depthOfField int
     */
    public void setDepthOfField(int _depthOfField) {
        this._depthOfField = _depthOfField;
    }

    /**
     * setter of _adaptiveSampling
     * @param adaptiveSampling
     */
    public void setAdaptiveSampling(int adaptiveSampling) {
        _adaptiveSampling = adaptiveSampling;
    }

    /**
     * recursive function that calculates the color of each pixel with adaptive super sampling
     * @param nx amount of pixels on X
     * @param ny amount of pixels on y
     * @param distance screen distance from camera
     * @param width screen width
     * @param height screen height
     * @param pixel pixel
     * @param level recursive level
     * @param reduce reduce the pixel into a sub pixel
     * @param center center of sub pixel
     * @return Color of a pixel
     */
    public primitives.Color adaptiveSamplingRecursion(int nx, int ny, double distance, double width, double height, Pixel pixel, int level, double reduce, Point3D center){
        Camera camera = _scene.getCamera();

        List<Ray> rays = camera.constructAdaptiveRayBeamThroughPixel(nx,ny,pixel.col,pixel.row,distance,width,height,reduce,center);

        primitives.Color centerColor;
        primitives.Color color1;
        primitives.Color color2;
        primitives.Color color3;
        primitives.Color color4;

        //calculates ray intersection color
        Ray rayCenter = rays.get(0);
        if (findClosestIntersection(rayCenter) == null)
            centerColor = _scene.getBackground();
        else
            centerColor = calcColor(findClosestIntersection(rayCenter),rayCenter);

        Ray ray1 = rays.get(1);
        if (findClosestIntersection(ray1) == null)
            color1 = _scene.getBackground();
        else
            color1 = calcColor(findClosestIntersection(ray1),ray1);

        Ray ray2 = rays.get(2);
        if (findClosestIntersection(ray2) == null)
            color2 = _scene.getBackground();
        else
            color2 = calcColor(findClosestIntersection(ray2),ray2);

        Ray ray3 = rays.get(3);
        if (findClosestIntersection(ray3) == null)
            color3 = _scene.getBackground();
        else
            color3 = calcColor(findClosestIntersection(ray3),ray3);

        Ray ray4 = rays.get(4);
        if (findClosestIntersection(ray4) == null)
            color4 = _scene.getBackground();
        else
            color4 = calcColor(findClosestIntersection(ray4),ray4);

        if(level == 0){ //end of recursion
            centerColor = centerColor.add(color1,color2,color3,color4);
            return centerColor.reduce(5);
        }

        //all colors are equal
        if(centerColor.equals(color1) && centerColor.equals(color2) && centerColor.equals(color3) && centerColor.equals(color4))
            return centerColor;
        else {//compares each color with center color, if not equal will call recursive function for sub pixel
            if(!color1.equals(centerColor)) {
                double x = (rayCenter.getP0().getX().get()+ray1.getP0().getX().get())/2d;
                double y = (rayCenter.getP0().getY().get()+ray1.getP0().getY().get())/2d;
                double z = (rayCenter.getP0().getZ().get()+ray1.getP0().getZ().get())/2d;
                Point3D p1 = new Point3D(x,y,z);
                color1 = adaptiveSamplingRecursion(nx, ny, distance, width, height, pixel, level - 1, reduce * 2, p1);
            }
            if(!color2.equals(centerColor)) {
                double x = (rayCenter.getP0().getX().get()+ray2.getP0().getX().get())/2d;
                double y = (rayCenter.getP0().getY().get()+ray2.getP0().getY().get())/2d;
                double z = (rayCenter.getP0().getZ().get()+ray2.getP0().getZ().get())/2d;
                Point3D p2 = new Point3D(x,y,z);
                color2 = adaptiveSamplingRecursion(nx, ny, distance, width, height, pixel, level - 1, reduce * 2, p2);
            }
            if(!color3.equals(centerColor)) {
                double x = (rayCenter.getP0().getX().get()+ray3.getP0().getX().get())/2d;
                double y = (rayCenter.getP0().getY().get()+ray3.getP0().getY().get())/2d;
                double z = (rayCenter.getP0().getZ().get()+ray3.getP0().getZ().get())/2d;
                Point3D p3 = new Point3D(x,y,z);
                color3 = adaptiveSamplingRecursion(nx, ny, distance, width, height, pixel, level - 1, reduce * 2, p3);
            }
            if(!color4.equals(centerColor)) {
                double x = (rayCenter.getP0().getX().get()+ray4.getP0().getX().get())/2d;
                double y = (rayCenter.getP0().getY().get()+ray4.getP0().getY().get())/2d;
                double z = (rayCenter.getP0().getZ().get()+ray4.getP0().getZ().get())/2d;
                Point3D p4 = new Point3D(x,y,z);
                color4 = adaptiveSamplingRecursion(nx, ny, distance, width, height, pixel, level - 1, reduce * 2, p4);
            }
        }

        centerColor = centerColor.add(color1,color2,color3,color4);
        return centerColor.reduce(5);

    }

    /**
     * Filling the buffer according to the geometries that are in the scene.
     * This function is not creating the picture file, but create the buffer pf pixels
     */
    public void renderImage() {
        primitives.Color background = _scene.getBackground();
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        double distance = _scene.getDistance();

        //width and height are the number of pixels in the rows
        //and columns of the view plane
        int width = (int) _imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();

        //Nx and Ny are the width and height of the image.
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();

        final Pixel thePixel = new Pixel(Ny, Nx);

        // Generate threads
        Thread[] threads = new Thread[_threads];

        for (int i = _threads - 1; i >= 0; --i) {
                threads[i] = new Thread(() -> {
                    Pixel pixel = new Pixel();
                    primitives.Color resultingColor;
                    while (thePixel.nextPixel(pixel)) {
                        if (_depthOfField == 0){
                            resultingColor = getPixelRayColor(camera, distance, Nx, Ny, width, height, pixel);
                        }
                        else {
                            if(_adaptiveSampling == 0)
                                resultingColor = getPixelRaysBeamColor(camera, distance, Nx, Ny, width, height, pixel);
                            else
                                resultingColor = adaptiveSamplingRecursion(Nx, Ny, distance,  width, height, pixel,3,2,Point3D.ZERO);
                        }
                        _imageWriter.writePixel(pixel.col, pixel.row, resultingColor.getColor());
                    }
                });
        }
        // Start threads
        for (Thread thread : threads) thread.start();
        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception e) {
            }
        }
        if (_print) {
            printMessage("\r100%\n");
        }

    }
    private synchronized void printMessage(String msg) {
        synchronized (System.out) {
            System.out.println(msg);
        }
    }

    /**
     * gets pixel color for ray
     * @param camera Camera
     * @param distance double
     * @param nx int
     * @param ny int
     * @param width double
     * @param height double
     * @param pixel Pixel
     * @return Color
     */
    private primitives.Color getPixelRayColor(Camera camera, double distance, int nx, int ny, double width, double height, Pixel pixel) {
        Ray ray = camera.constructRayThroughPixel(nx, ny, pixel.col, pixel.row, distance, width, height);
        GeoPoint closestPoint = findClosestIntersection(ray);
        primitives.Color resultingColor = _scene.getBackground();
        if (closestPoint != null) {
            resultingColor = calcColor(closestPoint, ray);
        }
        return resultingColor;
    }

    /**
     * gets pixel color for ray beam
     * @param camera Camera
     * @param distance double
     * @param nx int
     * @param ny int
     * @param width double
     * @param height double
     * @param pixel Pixel
     * @return Color
     */
    private primitives.Color getPixelRaysBeamColor(Camera camera, double distance, int nx, int ny, double width, double height, Pixel pixel) {
        Ray ray = camera.constructRayThroughPixel(nx, ny, pixel.col, pixel.row, distance, width, height);
        List<Ray> rayBeam = camera.constructRayBeam(nx, ny, pixel.col, pixel.row, distance, width, height);
        primitives.Color averageColor = calcolor(_scene.getBackground(), ray, rayBeam);
        return averageColor;
    }


    /**
     * help function: calculates the average color of the intersection points from all rays in a list
     * @param background Color
     * @param ray        Ray
     * @param rayBeam    List of Ray
     * @return Color
     */
    private primitives.Color calcolor(primitives.Color background, Ray ray, List<Ray> rayBeam) {
        GeoPoint closestPoint;
        primitives.Color averageColor = primitives.Color.BLACK;
        for (Ray r : rayBeam) {
            closestPoint = findClosestIntersection(r);
            if (closestPoint == null) {
                averageColor = averageColor.add(background);
            } else {
                averageColor = averageColor.add(calcColor(closestPoint, ray));
            }
        }
        averageColor = averageColor.reduce(rayBeam.size());
        return averageColor;
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
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param v          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private primitives.Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v,
                                          int nShininess, primitives.Color ip) {
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
     * calculates Reflection Ray
     *
     * @param pointGeo Point3D
     * @param inRay    Ray
     * @param n        Vector
     * @return Ray
     */
    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) {
        //r = v-2.(v.n).n
        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);
        if (vn == 0) {
            return null;
        }
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }

    /**
     * calculates Refraction ray
     *
     * @param pointGeo Point3D
     * @param inRay    Ray
     * @param n        Vector
     * @return Ray
     */
    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.getDir(), n);
    }

    /**
     * Finding the closest point to the P0 of the camera.
     *
     * @param intersectionPoints list of points, the function should find from
     *                           this list the closet point to P0 of the camera in the scene.
     * @return the closest point to the camera
     * change to public for test
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
        GeoPoint result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.getCamera().getP0();

        for (GeoPoint geo : intersectionPoints) {
            Point3D pt = geo.getPoint();
            double distance = p0.distance(pt);
            if (distance < mindist) {
                mindist = distance;
                result = geo;
            }
        }
        return result;
    }

    /**
     * Printing the grid with a fixed interval between lines
     *
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval, Color colorsep) {
        double rows = this._imageWriter.getNx();
        double columns = _imageWriter.getNy();
        //Writing the lines.
        for (int col = 0; col < columns; col++) {
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
     *
     * @param l        Vector
     * @param n        Vector
     * @param geopoint GeoPoint
     * @return boolean
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint.getPoint());
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.getPoint().distance(geopoint.getPoint()) - lightDistance) <= 0 && gp.getGeometry().getMaterial().getKt() == 0)
                return false;
        }
        return true;
    }

    /**
     * calculates the closest point to the rays head
     *
     * @param ray Ray
     * @return GeoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        if (ray == null) {
            return null;
        }
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.getP0();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.getPoint());
            if (distance < closestDistance) {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }

    /**
     * calls the recursive func to calculates a color in a point
     *
     * @param geoPoint GeoPoint
     * @param inRay    Ray
     * @return Color
     */
    private primitives.Color calcColor(GeoPoint geoPoint, Ray inRay) {
        primitives.Color color = calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
        color = color.add(_scene.getAmbientLight().getIntensity());
        return color;
    }

    /**
     * recursive func: calculates the color in a point
     *
     * @param geoPoint GeoPoint
     * @param inRay    Ray
     * @param level    int
     * @param k        double
     * @return Color
     */
    private primitives.Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
        if (level == 1 || k < MIN_CALC_COLOR_K) {
            return primitives.Color.BLACK;
        }

        primitives.Color result = geoPoint.getGeometry().getEmission();
        Point3D pointGeo = geoPoint.getPoint();

        Vector v = pointGeo.subtract(_scene.getCamera().getP0()).normalize();
        Vector n = geoPoint.getGeometry().getNormal(pointGeo);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            //ray parallel to geometry surface ??
            //and orthogonal to normal
            return result;
        }

        Material material = geoPoint.getGeometry().getMaterial();
        int nShininess = material.getNShininess();
        double kd = material.getKd();
        double ks = material.getKs();
        double kr = geoPoint.getGeometry().getMaterial().getKr();
        double kt = geoPoint.getGeometry().getMaterial().getKt();
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
     *
     * @param geoPoint   GeoPoint
     * @param k          double
     * @param result     Color
     * @param v          Vector
     * @param n          Vector
     * @param nv         double
     * @param nShininess int
     * @param kd         double
     * @param ks         double
     * @return Color
     */
    private primitives.Color getLightSourcesColors(GeoPoint geoPoint, double k, primitives.Color result, Vector
            v, Vector n, double nv, int nShininess, double kd, double ks) {
        Point3D pointGeo = geoPoint.getPoint();
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
     *
     * @param light    LightSource
     * @param l        Vector
     * @param n        Vector
     * @param geopoint GeoPoint
     * @return double
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        Point3D pointGeo = geopoint.getPoint();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return 1d;
        }
        double lightDistance = light.getDistance(pointGeo);
        double ktr = 1d;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0) {
                ktr *= gp.getGeometry().getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }
        }
        return ktr;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br>
     * There is a main follow up object and several secondary objects - one in each thread.
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

}
