package dev.sergevas.iot.env.application.service;

import dev.sergevas.iot.env.application.port.in.Bmp180UseCase;
import dev.sergevas.iot.env.application.port.out.BMP180Spec;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Readings;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Bmp180Service implements Bmp180UseCase {

    @Inject
    BMP180Spec bmp180Spec;

    @Loggable(logReturnVal = true)
    @Override
    public double getTemperature() {
        return bmp180Spec.readTemperature();
    }

    @Loggable(logReturnVal = true)
    @Override
    public double getPressure() {
        return bmp180Spec.readPressure();
    }

    @Loggable(logReturnVal = true)
    @Override
    public Bmp180Readings getSensorReadings() {
        return new Bmp180Readings(bmp180Spec.readTemperature(), bmp180Spec.readPressure(), bmp180Spec.readChipId());
    }

    @Loggable
    @Override
    public String getChipId() {
        return bmp180Spec.readChipId();
    }

    @Loggable
    @Override
    public void reset() {
        bmp180Spec.softReset();
    }
}
