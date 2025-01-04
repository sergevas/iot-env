package dev.sergevas.iot.env.adapter.out.i2c.bmp180;

import dev.sergevas.iot.env.application.port.out.BMP180Spec;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Readings;
import dev.sergevas.iot.env.domain.bmp180.Calibration;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@DefaultBean
@ApplicationScoped
public class Bmp180AdapterMock implements BMP180Spec {

    @Loggable(logReturnVal = true)
    @Override
    public Calibration readCalibration() {
        return null;
    }

    @Loggable(logReturnVal = true)
    @Override
    public Bmp180Readings readTemperatureAndPressure() {
        return new Bmp180Readings(24.6, 99325.1786);
    }
}
