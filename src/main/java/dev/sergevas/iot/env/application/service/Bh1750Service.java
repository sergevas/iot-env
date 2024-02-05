package dev.sergevas.iot.env.application.service;

import dev.sergevas.iot.env.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.application.port.out.LightIntensity;
import dev.sergevas.iot.env.domain.Bh1750Readings;

public class Bh1750Service implements Bh1750UseCase {
    private final LightIntensity lightIntensity;

    public Bh1750Service(LightIntensity lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public Bh1750Readings getSensorReadingsItemTypeForBh1750() {
        return new Bh1750Readings(lightIntensity.getLightIntensity(), DateTimeGen.now());
    }
}
