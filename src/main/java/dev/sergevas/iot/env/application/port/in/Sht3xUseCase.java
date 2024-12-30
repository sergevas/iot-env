package dev.sergevas.iot.env.application.port.in;

import dev.sergevas.iot.env.domain.sht3x.HeaterState;
import dev.sergevas.iot.env.domain.sht3x.Sht3xReadings;
import dev.sergevas.iot.env.domain.sht3x.StatusRegister;

public interface Sht3xUseCase {

    Sht3xReadings getSensorReadings();

    StatusRegister getStatus();

    boolean updateHeaterState(HeaterState heaterState);


}
