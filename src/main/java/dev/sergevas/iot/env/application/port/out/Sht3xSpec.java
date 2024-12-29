package dev.sergevas.iot.env.application.port.out;

import dev.sergevas.iot.env.domain.sht3x.HeaterState;
import dev.sergevas.iot.env.domain.sht3x.Sht3xReadings;

public interface Sht3xSpec {

    void softReset();

    boolean updateHeater(HeaterState heaterState);

    Sht3xReadings readTemperatureAndHumidity();
}
