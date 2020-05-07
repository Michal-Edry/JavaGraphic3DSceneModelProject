package primitives;

public class Material {
    double _kD;
    double _kS;
    int _nShininess;

    /**
     * constructor
     * @param _kD double
     * @param _kS double
     * @param _nShininess double
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    /**
     * getter for _kD
     * @return double
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * getter for _kS
     * @return double
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * getter for _nShininess
     * @return int
     */
    public int get_nShininess() {
        return _nShininess;
    }
}
