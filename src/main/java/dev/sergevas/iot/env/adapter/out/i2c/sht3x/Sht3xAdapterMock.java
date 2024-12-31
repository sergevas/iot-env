package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import dev.sergevas.iot.env.application.port.out.Sht3xSpec;
import dev.sergevas.iot.env.domain.sht3x.HeaterState;
import dev.sergevas.iot.env.domain.sht3x.Sht3xReadings;
import dev.sergevas.iot.env.domain.sht3x.StatusRegister;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkus.arc.DefaultBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class Sht3xAdapterMock implements Sht3xSpec {

    private StatusRegister statusRegister;

    @PostConstruct
    public void init() {
        statusRegister = new StatusRegister(
                (byte) 0,
                (byte) 0,
                (byte) 0,
                (byte) 0,
                (byte) 0,
                (byte) 0,
                (byte) 0);
    }

    @Loggable
    @Override
    public void softReset() {
    }

    @Loggable(logArguments = true, logReturnVal = true)
    @Override
    public boolean updateHeater(HeaterState heaterState) {
        return true;
    }

    @Loggable(logReturnVal = true)
    @Override
    public Sht3xReadings readTemperatureAndHumidity() {
        return new Sht3xReadings(2.48, 53.71);
    }

    @Loggable(logReturnVal = true)
    @Override
    public StatusRegister readStatus() {
        return statusRegister;
    }

    @Loggable(logReturnVal = true)
    @Override
    public StatusRegister clearStatus() {
        return readStatus();
    }
}
