package dev.sergevas.iot.env.application.service;

import dev.sergevas.iot.env.application.port.in.bh1750.Bh1750UseCase;
import dev.sergevas.iot.env.application.port.out.LightIntensityReader;
import dev.sergevas.iot.env.domain.Bh1750Readings;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Bh1750Service implements Bh1750UseCase {

    @Inject
    LightIntensityReader lightIntensityReader;

    @Loggable
    public Bh1750Readings getSensorReadingsItemTypeForBh1750() {
        return new Bh1750Readings().lightIntensity(lightIntensityReader.readLightIntensity());
    }
}
