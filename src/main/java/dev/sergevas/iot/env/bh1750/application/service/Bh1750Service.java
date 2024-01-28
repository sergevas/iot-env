package dev.sergevas.iot.env.bh1750.application.service;

import dev.sergevas.iot.env.bh1750.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.bh1750.application.port.out.LightIntensity;
import dev.sergevas.iot.env.bh1750.domain.Bh1750Readings;
import dev.sergevas.iot.env.infrastructure.time.DateTimeGen;

public class Bh1750Service implements Bh1750UseCase {
    private final LightIntensity lightIntensity;

    public Bh1750Service(LightIntensity lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public Bh1750Readings getSensorReadingsItemTypeForBh1750() {
        return new Bh1750Readings(lightIntensity.getLightIntensity(), DateTimeGen.now());
    }
}
