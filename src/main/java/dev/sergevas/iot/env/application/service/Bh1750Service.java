package dev.sergevas.iot.env.application.service;

import dev.sergevas.iot.env.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.application.port.out.LightIntensityFetcher;
import dev.sergevas.iot.env.domain.Bh1750Readings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Bh1750Service implements Bh1750UseCase {
    private final LightIntensityFetcher lightIntensityFetcher;

    @Inject
    public Bh1750Service(LightIntensityFetcher lightIntensity) {
        this.lightIntensityFetcher = lightIntensity;
    }

    public Bh1750Readings getSensorReadingsItemTypeForBh1750() {
        return new Bh1750Readings().lightIntensity(lightIntensityFetcher.getLightIntensity());
    }
}
