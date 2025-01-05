package dev.sergevas.iot.env.domain.bmp180;

import dev.sergevas.iot.env.application.port.out.SensorException;
import org.junit.jupiter.api.Test;

import static dev.sergevas.iot.env.domain.bmp180.Calibration.CalibrationRegister.AC1_MSB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class CalibrationTest {

    @Test
    void validate() {
        byte b = 0x7f;
        assertEquals(b, Calibration.validate(b, AC1_MSB));
        assertEquals("BMP180 calibration coefficients validation error: 00 for AC1_MSB",
                assertThrowsExactly(SensorException.class, () -> Calibration.validate((byte) 0x00, AC1_MSB)).getMessage());
        assertEquals("BMP180 calibration coefficients validation error: FF for AC1_MSB",
                assertThrowsExactly(SensorException.class, () -> Calibration.validate((byte) 0xFF, AC1_MSB)).getMessage());
    }
}
