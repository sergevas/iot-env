package dev.sergevas.iot.env.domain.bmp180;

import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.service.shared.StringUtil;

import java.util.Objects;
import java.util.StringJoiner;

import static dev.sergevas.iot.env.domain.ErrorEvent.E_BMP180_0003;
import static dev.sergevas.iot.env.domain.SensorName.BMP180;
import static dev.sergevas.iot.env.domain.SensorType.TEMP_PRESS;

public class Calibration {

    /*  BST-BMP180-DS000-12 Table 5: Calibration coefficients
       | Parameter | MSB  | LSB  |
       |    AC1    | 0xAA | 0xAB |
       |    AC2    | 0xAC | 0xAD |
       |    AC3    | 0xAE | 0xAF |
       |    AC4    | 0xB0 | 0xB1 |
       |    AC5    | 0xB2 | 0xB3 |
       |    AC6    | 0xB4 | 0xB5 |
       |     B1    | 0xB6 | 0xB7 |
       |     B2    | 0xB8 | 0xB9 |
       |     MB    | 0xBA | 0xBB |
       |     MC    | 0xBC | 0xBD |
       |     MD    | 0xBE | 0xBF |
   */

    public enum CalibrationRegister {

        AC1_MSB(0xAA),
        AC1_LSB(0xAB),
        AC2_MSB(0xAC),
        AC2_LSB(0xAD),
        AC3_MSB(0xAE),
        AC3_LSB(0xAF),
        AC4_MSB(0xB0),
        AC4_LSB(0xB1),
        AC5_MSB(0xB2),
        AC5_LSB(0xB3),
        AC6_MSB(0xB4),
        AC6_LSB(0xB5),
        B1_MSB(0xB6),
        B1_LSB(0xB7),
        B2_MSB(0xB8),
        B2_LSB(0xB9),
        MB_MSB(0xBA),
        MB_LSB(0xBB),
        MC_MSB(0xBC),
        MC_LSB(0xBD),
        MD_MSB(0xBE),
        MD_LSB(0xBF);

        private final int address;

        CalibrationRegister(int address) {
            this.address = address;
        }

        public int address() {
            return address;
        }
    }

    private int _AC1;
    private int _AC2;
    private int _AC3;
    private int _AC4;
    private int _AC5;
    private int _AC6;
    private int _B1;
    private int _B2;
    private int _MB;
    private int _MC;
    private int _MD;

    public int getAC1() {
        return _AC1;
    }

    public Calibration ac1(int _AC1) {
        this._AC1 = _AC1;
        return this;
    }

    public int getAC2() {
        return _AC2;
    }

    public Calibration ac2(int _AC2) {
        this._AC2 = _AC2;
        return this;
    }

    public int getAC3() {
        return _AC3;
    }

    public Calibration ac3(int _AC3) {
        this._AC3 = _AC3;
        return this;
    }

    public int getAC4() {
        return _AC4;
    }

    public Calibration ac4(int _AC4) {
        this._AC4 = _AC4;
        return this;
    }

    public int getAC5() {
        return _AC5;
    }

    public Calibration ac5(int _AC5) {
        this._AC5 = _AC5;
        return this;
    }

    public int getAC6() {
        return _AC6;
    }

    public Calibration ac6(int _AC6) {
        this._AC6 = _AC6;
        return this;
    }

    public int getB1() {
        return _B1;
    }

    public Calibration b1(int _B1) {
        this._B1 = _B1;
        return this;
    }

    public int getB2() {
        return _B2;
    }

    public Calibration b2(int _B2) {
        this._B2 = _B2;
        return this;
    }

    public int getMB() {
        return _MB;
    }

    public Calibration mb(int _MB) {
        this._MB = _MB;
        return this;
    }

    public int getMC() {
        return _MC;
    }

    public Calibration mc(int _MC) {
        this._MC = _MC;
        return this;
    }

    public int getMD() {
        return _MD;
    }

    public Calibration md(int _MD) {
        this._MD = _MD;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Calibration that = (Calibration) o;
        return _AC1 == that._AC1 && _AC2 == that._AC2 && _AC3 == that._AC3 && _AC4 == that._AC4
                && _AC5 == that._AC5 && _AC6 == that._AC6 && _B1 == that._B1 && _B2 == that._B2
                && _MB == that._MB && _MC == that._MC && _MD == that._MD;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_AC1, _AC2, _AC3, _AC4, _AC5, _AC6, _B1, _B2, _MB, _MC, _MD);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Calibration.class.getSimpleName() + "[", "]")
                .add("_AC1=" + _AC1)
                .add("_AC2=" + _AC2)
                .add("_AC3=" + _AC3)
                .add("_AC4=" + _AC4)
                .add("_AC5=" + _AC5)
                .add("_AC6=" + _AC6)
                .add("_B1=" + _B1)
                .add("_B2=" + _B2)
                .add("_MB=" + _MB)
                .add("_MC=" + _MC)
                .add("_MD=" + _MD)
                .toString();
    }

    /*
     * BST-BMP180-DS000-12 3.4 Calibration coefficients
     * The data communication can be checked by checking that none of the words has the value 0 or 0xFFFF
     */
    public static byte validate(byte b, CalibrationRegister register) {
        if (b == 0x00 || b == (byte) 0xFF) {
            throw new SensorException(
                    E_BMP180_0003.getId(),
                    TEMP_PRESS,
                    BMP180,
                    E_BMP180_0003.getName() + String.format(": %s for %s", StringUtil.toHexString(b), register));
        }
        return b;
    }
}
