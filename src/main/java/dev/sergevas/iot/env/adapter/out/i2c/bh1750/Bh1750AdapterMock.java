package dev.sergevas.iot.env.adapter.out.i2c.bh1750;

import dev.sergevas.iot.env.application.port.out.LightIntensityFetcher;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class Bh1750AdapterMock implements LightIntensityFetcher {


    @Override
    public double getLightIntensity() {
        return 567.89;
    }
}
