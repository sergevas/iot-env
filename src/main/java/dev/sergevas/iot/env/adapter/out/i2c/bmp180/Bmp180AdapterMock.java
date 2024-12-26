package dev.sergevas.iot.env.adapter.out.i2c.bmp180;

import dev.sergevas.iot.env.application.port.out.PressureReader;
import dev.sergevas.iot.env.application.port.out.TemperatureReader;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@DefaultBean
@ApplicationScoped
public class Bmp180AdapterMock implements TemperatureReader, PressureReader {
    @Override
    public double getPressure() {
        return 0;
    }

    @Override
    public double readTemperature() {
        return 0;
    }
}
