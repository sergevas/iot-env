package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import dev.sergevas.iot.env.application.port.out.Sht3xSpec;
import dev.sergevas.iot.env.domain.Sht3xReadings;
import io.quarkus.arc.DefaultBean;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class Sht3xAdapterMock implements Sht3xSpec {

    @Override
    public void softReset() {
        Log.debugf("SHT3x send soft reset command");
    }

    @Override
    public Sht3xReadings readTemperatureAndHumidity() {
        return new Sht3xReadings(2.48, 53.71);
    }
}
