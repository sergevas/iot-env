package dev.sergevas.iot.env.adapter.out.i2c.bh1750;

import dev.sergevas.iot.env.application.port.out.LightIntensity;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class Bh1750AdapterMock implements LightIntensity {


    @Override
    public double getLightIntensity() {
        return 567.89;
    }
}
