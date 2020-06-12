package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * abstract class of geometries
 */
public abstract class Geometry implements Intersectable {

    protected Color _emission;
    protected Material _material;


    /**
     * default constructor
     */
    public Geometry(){
        this(Color.BLACK, new Material(0,0,0));
    }

    /**
     * constructor
     * @param _emission Color
     */
    public Geometry(Color _emission) {
        this(_emission, new Material(0,0,0));
    }

    /**
     * constructor
     * @param _emission Color
     * @param _material Material
     */
    public Geometry(Color _emission, Material _material) {
        this._emission = _emission;
        this._material = _material;
    }

    /**
     * getter for emission
     * @return Color
     */
    public Color getEmission() {
        return _emission;
    }
    /**
     * getter for _material
     * @return Material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * abstract func for get normal
     * @param _p Point3D
     * @return Vector
     */
    public abstract Vector getNormal(Point3D _p);
}
