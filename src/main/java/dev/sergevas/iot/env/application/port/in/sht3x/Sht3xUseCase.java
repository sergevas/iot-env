package dev.sergevas.iot.env.application.port.in.sht3x;

import dev.sergevas.iot.env.domain.sht3x.HeaterState;
import dev.sergevas.iot.env.domain.sht3x.Sht3xReadings;
import dev.sergevas.iot.env.domain.sht3x.StatusRegister;

public interface Sht3xUseCase {

    Sht3xReadings getSensorReadings();

    StatusRegister getStatus();

    StatusRegister clearStatus();

    boolean updateHeaterState(HeaterState heaterState);

    void reset();
}
