package elements;

import primitives.Color;

/**
 * class of Ambient Light
 */
public class AmbientLight extends Light{

    /**
     * constructor
     * @param _intensity Color
     * @param _kA double
     */
    public AmbientLight(Color _intensity, double _kA) {
        super(_intensity.scale(_kA));
    }

}
