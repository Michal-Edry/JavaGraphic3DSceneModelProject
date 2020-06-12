package elements;

import primitives.Color;

/**
 * abstract class of Light
 */
abstract class Light {
    protected Color _intensity;

    /**
     * constructor
     * @param _intensity Color
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * getter for _intensity
     * @return Color
     */
    public Color getIntensity() {
        return _intensity;
    }
}
