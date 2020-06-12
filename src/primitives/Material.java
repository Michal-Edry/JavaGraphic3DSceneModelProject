package primitives;

public class Material {
    double _kD;
    double _kS;
    int _nShininess;
    double _kT;
    double _kR;

    /**
     * constructor
     * @param _kD double
     * @param _kS double
     * @param _nShininess int
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this(_kD,_kS,_nShininess,0,0);
    }

    /**
     * constructor
     * @param _kD double
     * @param _kS double
     * @param _nShininess int
     * @param _kT double
     * @param _kR double
     */
    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
        this._kT = _kT;
        this._kR = _kR;
    }

    /**
     * getter for _kD
     * @return double
     */
    public double getKd() {
        return _kD;
    }

    /**
     * getter for _kS
     * @return double
     */
    public double getKs() {
        return _kS;
    }

    /**
     * getter for _nShininess
     * @return int
     */
    public int getNShininess() {
        return _nShininess;
    }

    /**
     * getter for _kT
     * @return double
     */
    public double getKt() {
        return _kT;
    }

    /**
     * getter for _kR
     * @return double
     */
    public double getKr() {
        return _kR;
    }
}
