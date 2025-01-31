package dev.sergevas.iot.env.application.port.out;

import dev.sergevas.iot.env.domain.bmp180.Calibration;

public interface BMP180Spec {

    Calibration readCalibration();

    int readUncompensatedTemperature();

    int readUncompensatedPressure();

    String readChipId();

    void softReset();
}
