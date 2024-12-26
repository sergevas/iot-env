package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import dev.sergevas.iot.env.application.port.out.HumidityReader;
import dev.sergevas.iot.env.application.port.out.LightIntensityReader;
import dev.sergevas.iot.env.application.port.out.TemperatureReader;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class Sht3xAdapterMock implements TemperatureReader, HumidityReader {

    @Override
    public double readTemperature() {
        return 2.48;
    }

    @Override
    public double readHumidity() {
        return 53.71;
    }
}
