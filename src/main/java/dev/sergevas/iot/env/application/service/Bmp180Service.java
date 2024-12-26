package dev.sergevas.iot.env.application.service;

import dev.sergevas.iot.env.application.port.in.Bmp180UseCase;
import dev.sergevas.iot.env.application.port.out.PressureReader;
import dev.sergevas.iot.env.application.port.out.TemperatureReader;
import dev.sergevas.iot.env.domain.Bmp180Readings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Bmp180Service implements Bmp180UseCase {
    private final TemperatureReader temperatureReader;
    private final PressureReader pressureReader;

    @Inject
    public Bmp180Service(TemperatureReader temperatureReader, PressureReader pressureReader) {
        this.pressureReader = pressureReader;
        this.temperatureReader = temperatureReader;
    }

    @Override
    public Bmp180Readings getSensorReadingsItemTypeForBmp180() {
        return new Bmp180Readings(temperatureReader.readTemperature(), pressureReader.getPressure());
    }
}
