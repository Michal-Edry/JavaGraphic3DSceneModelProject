package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Plane;
import primitives.Color;

import java.util.*;

/**
 * class of Scene
 */
public class Scene {

    private String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometries _geometries = new Geometries();
    private Camera _camera;
    private double _distance;
    private List<LightSource> _lights = new LinkedList<LightSource>();



    /**
     * constructor
     * @param _name String
     */
    public Scene(String _name) {
        this._name = _name;
    }

    /**
     * getter for name
     * @return String
     */
    public String getName() {
        return _name;
    }

    /**
     * getter for background
     * @return Color
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * getter for ambient light
     * @return AmbientLight
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * getter for geometries
     * @return Geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * getter for camera
     * @return Camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * getter for distance
     * @return double
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * getter for _lights
     * @return List of LightSource
     */
    public List<LightSource> getLightSources() {
        return _lights;
    }

    /**
     * setter for background
     * @param _background Color
     */
    public void setBackground(Color _background) {
        this._background = _background;
    }

    /**
     * setter for ambient light
     * @param _ambientLight AmbientLight
     */
    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * setter for camera
     * @param _camera Camera
     */
    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * setter for distance
     * @param _distance double
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    /**
     * adds geometries to the geometry collection
     * @param geometries
     */
    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }

    /**
     * adds light sources to lights list
     * @param lights LightSource...
     */
    public void addLights(LightSource... lights) {
        for (LightSource light : lights) {
            _lights.add(light);
        }
    }
}
