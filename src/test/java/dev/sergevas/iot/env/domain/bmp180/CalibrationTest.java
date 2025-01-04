package dev.sergevas.iot.env.domain.bmp180;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalibrationTest {

    @Test
    void isNotValid() {
        var calibration = new Calibration();
        assertTrue(calibration.isNotValid());
        var readings = calibration.getCalibrationReadings();
        for (int i = 0; i < readings.length - 1; i++) {
            readings[i] = (byte) (i + 1);
        }
        assertTrue(calibration.isNotValid());
        readings[readings.length - 1] = (byte) (1);
        assertFalse(calibration.isNotValid());
    }
}
