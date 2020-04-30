package elements;

import primitives.Color;

/**
 * class of Ambient Light
 */
public class AmbientLight {
    private Color _intensity;

    /**
     * constructor
     * @param _intensity Color
     * @param _kA double
     */
    public AmbientLight(Color _intensity, double _kA) {
        // ka is always 1 so we don't use it
        this._intensity = _intensity;
    }

    /**
     * getter for Intensity
     * @return color
     */
    public java.awt.Color getIntensity(){
        return  _intensity.getColor();
    }
}
