package dev.sergevas.iot.env.application.service;

import dev.sergevas.iot.env.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.application.port.out.LightIntensityReader;
import dev.sergevas.iot.env.domain.Bh1750Readings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Bh1750Service implements Bh1750UseCase {
    private final LightIntensityReader lightIntensityReader;

    @Inject
    public Bh1750Service(LightIntensityReader lightIntensity) {
        this.lightIntensityReader = lightIntensity;
    }

    public Bh1750Readings getSensorReadingsItemTypeForBh1750() {
        return new Bh1750Readings().lightIntensity(lightIntensityReader.readLightIntensity());
    }
}
