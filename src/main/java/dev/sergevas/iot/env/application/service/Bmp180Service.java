package dev.sergevas.iot.env.application.service;

import dev.sergevas.iot.env.application.port.in.Bmp180UseCase;
import dev.sergevas.iot.env.application.port.out.PressureFetcher;
import dev.sergevas.iot.env.application.port.out.TemperatureFetcher;
import dev.sergevas.iot.env.domain.Bmp180Readings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Bmp180Service implements Bmp180UseCase {
    private final TemperatureFetcher temperatureFetcher;
    private final PressureFetcher pressureFetcher;

    @Inject
    public Bmp180Service(TemperatureFetcher temperatureFetcher, PressureFetcher pressureFetcher) {
        this.pressureFetcher = pressureFetcher;
        this.temperatureFetcher = temperatureFetcher;
    }

    @Override
    public Bmp180Readings getSensorReadingsItemTypeForBmp180() {
        return new Bmp180Readings(temperatureFetcher.getTemperature(), pressureFetcher.getPressure());
    }
}
