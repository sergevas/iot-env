package dev.sergevas.iot.env.adapter.out.i2c.bmp180;

import dev.sergevas.iot.env.application.port.out.PressureFetcher;
import dev.sergevas.iot.env.application.port.out.TemperatureFetcher;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@DefaultBean
@ApplicationScoped
public class Bmp180AdapterMock implements TemperatureFetcher, PressureFetcher {
    @Override
    public double getPressure() {
        return 0;
    }

    @Override
    public double getTemperature() {
        return 0;
    }
}
