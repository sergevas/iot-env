package dev.sergevas.iot.env.application.port.out;

import dev.sergevas.iot.env.domain.Sht3xReadings;

public interface Sht3xSpec {

    Sht3xReadings readTemperatureAndHumidity();
}
