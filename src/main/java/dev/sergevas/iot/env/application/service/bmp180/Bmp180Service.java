package dev.sergevas.iot.env.application.service.bmp180;

import dev.sergevas.iot.env.application.port.in.bmp180.Bmp180UseCase;
import dev.sergevas.iot.env.application.port.out.BMP180Spec;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Pressure;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Readings;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Temperature;
import dev.sergevas.iot.env.domain.bmp180.Calibration;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Bmp180Service implements Bmp180UseCase {

    @Inject
    BMP180Spec bmp180Spec;

    @Inject
    Bmp180TrueReadingsCalculator bmp180TrueReadingsCalculator;

    private Calibration calibration;
    private String chipId;

    @PostConstruct
    public void init() {
        this.calibration = bmp180Spec.readCalibration();
    }

    @Loggable(logReturnVal = true)
    @Override
    public Bmp180Temperature getTemperature() {
        return new Bmp180Temperature(bmp180TrueReadingsCalculator.calculateTrueTemperatureInTenths(calibration,
                bmp180Spec.readUncompensatedTemperature()));
    }

    @Loggable(logReturnVal = true)
    @Override
    public Bmp180Pressure getPressure() {
        return new Bmp180Pressure(bmp180TrueReadingsCalculator.calculateTruePressure(calibration,
                bmp180Spec.readUncompensatedTemperature(), bmp180Spec.readUncompensatedPressure()));
    }

    @Loggable(logReturnVal = true)
    @Override
    public Bmp180Readings getSensorReadings() {
        var chipId = getChipId();
        var uncompensatedTemperature = bmp180Spec.readUncompensatedTemperature();
        var uncompensatedPressure = bmp180Spec.readUncompensatedPressure();
        var temp = bmp180TrueReadingsCalculator.calculateTrueTemperatureInTenths(calibration, uncompensatedTemperature);
        var pressPa = bmp180TrueReadingsCalculator.calculateTruePressure(calibration, uncompensatedTemperature,
                uncompensatedPressure);
        return new Bmp180Readings(new Bmp180Temperature(temp), new Bmp180Pressure(pressPa), chipId);
    }

    @Loggable
    @Override
    public String getChipId() {
        if (chipId == null) {
            chipId = bmp180Spec.readChipId();
        }
        return chipId;
    }

    @Loggable
    @Override
    public void reset() {
        bmp180Spec.softReset();
    }
}
