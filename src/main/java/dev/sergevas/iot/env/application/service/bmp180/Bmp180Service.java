package dev.sergevas.iot.env.application.service.bmp180;

import dev.sergevas.iot.env.application.port.in.Bmp180UseCase;
import dev.sergevas.iot.env.application.port.out.BMP180Spec;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Readings;
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
    public double getTemperature() {
        return bmp180TrueReadingsCalculator.calculateTrueTemperature(calibration,
                bmp180Spec.readUncompensatedTemperature());
    }

    @Loggable(logReturnVal = true)
    @Override
    public double getPressure() {
        return bmp180TrueReadingsCalculator.calculateTruePressure(calibration,
                bmp180Spec.readUncompensatedTemperature(), bmp180Spec.readUncompensatedPressure());
    }

    @Loggable(logReturnVal = true)
    @Override
    public Bmp180Readings getSensorReadings() {
        var chipId = getChipId();
        var uncompensatedTemperature = bmp180Spec.readUncompensatedTemperature();
        var uncompensatedPressure = bmp180Spec.readUncompensatedTemperature();
        return new Bmp180Readings(
                bmp180TrueReadingsCalculator.calculateTrueTemperature(calibration, uncompensatedTemperature),
                bmp180TrueReadingsCalculator.calculateTruePressure(calibration, uncompensatedTemperature,
                        uncompensatedPressure),
                chipId);
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
