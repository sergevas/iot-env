package dev.sergevas.iot.env.application.port.bmp180;

import dev.sergevas.iot.env.domain.bmp180.Bmp180Pressure;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Readings;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Temperature;

public interface Bmp180UseCase {

    Bmp180Temperature getTemperature();

    Bmp180Pressure getPressure();

    Bmp180Readings getSensorReadings();

    String getChipId();

    void reset();
}
