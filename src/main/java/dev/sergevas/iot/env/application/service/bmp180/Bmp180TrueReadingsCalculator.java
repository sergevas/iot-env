package dev.sergevas.iot.env.application.service.bmp180;

import dev.sergevas.iot.env.domain.bmp180.Calibration;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Bmp180TrueReadingsCalculator {

    public double calculateTrueTemperature(Calibration calibration, int uncompensatedTemp) {
        double x1 = (uncompensatedTemp - calibration.getAC6()) * calibration.getAC5() / 32768.0;
        double x2 = calibration.getMC() * 2048.0 / (x1 + calibration.getMD());
        double b5 = x1 + x2;
        return (b5 + 8) / 160;
    }

    // TODO: implement
    public double calculateTruePressure(Calibration calibration, int uncompensatedTemp, int uncompensatedPressure) {
        return 0.0;
    }
}
