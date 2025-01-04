package dev.sergevas.iot.env.domain.bmp180;

public class Calibration {

    public static final int BMP180_CALIBRATION_DATA_LENGTH = 22;

    private final byte[] calibrationReadings = new byte[BMP180_CALIBRATION_DATA_LENGTH];

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

    public byte[] getCalibrationReadings() {
        return calibrationReadings;
    }

    //    SEE: BST-BMP180-DS000-12 3.4 Calibration coefficients
//    The data communication can be checked by checking that none of the words has the value 0 or 0xFFFF
    public boolean isNotValid() {
        for (byte b : calibrationReadings) {
            if (b == 0x00 || b == (byte) 0xFF) {
                return true;
            }
        }
        return false;
    }
}
