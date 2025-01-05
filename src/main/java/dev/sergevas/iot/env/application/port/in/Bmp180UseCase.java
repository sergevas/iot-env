package dev.sergevas.iot.env.application.port.in;

import dev.sergevas.iot.env.domain.bmp180.Bmp180Readings;

public interface Bmp180UseCase {

    double getTemperature();

    double getPressure();

    Bmp180Readings getSensorReadings();

    String getChipId();

    void reset();
}
