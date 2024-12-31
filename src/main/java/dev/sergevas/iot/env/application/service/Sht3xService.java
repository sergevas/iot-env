package dev.sergevas.iot.env.application.service;

import dev.sergevas.iot.env.application.port.in.Sht3xUseCase;
import dev.sergevas.iot.env.application.port.out.Sht3xSpec;
import dev.sergevas.iot.env.domain.sht3x.HeaterState;
import dev.sergevas.iot.env.domain.sht3x.Sht3xReadings;
import dev.sergevas.iot.env.domain.sht3x.StatusRegister;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Sht3xService implements Sht3xUseCase {

    @Inject
    Sht3xSpec sht3xSpec;

    @Loggable
    @Override
    public Sht3xReadings getSensorReadings() {
        return sht3xSpec.readTemperatureAndHumidity();
    }

    @Loggable
    @Override
    public StatusRegister getStatus() {
        return sht3xSpec.readStatus();
    }

    @Loggable
    @Override
    public StatusRegister clearStatus() {
        return sht3xSpec.clearStatus();
    }

    @Loggable(logArguments = true)
    @Override
    public boolean updateHeaterState(HeaterState heaterState) {
        return sht3xSpec.updateHeater(heaterState);
    }

    @Loggable
    @Override
    public void reset() {
        sht3xSpec.softReset();
    }
}
