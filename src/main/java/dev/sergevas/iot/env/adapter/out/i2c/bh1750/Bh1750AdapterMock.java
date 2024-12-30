package dev.sergevas.iot.env.adapter.out.i2c.bh1750;

import dev.sergevas.iot.env.application.port.out.LightIntensityReader;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class Bh1750AdapterMock implements LightIntensityReader {

    @Override
    public double readLightIntensity() {
        return 567.89;
    }
}
