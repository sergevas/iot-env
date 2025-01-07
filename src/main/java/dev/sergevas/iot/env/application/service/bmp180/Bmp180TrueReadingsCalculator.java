package dev.sergevas.iot.env.application.service.bmp180;

import dev.sergevas.iot.env.domain.bmp180.Calibration;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Bmp180TrueReadingsCalculator {

    public double calculateTrueTemperature(Calibration calibration, int uncompensatedTemp) {

        return (b5(calibration, uncompensatedTemp) + 8) / 160;
    }

    // TODO: implement
    public double calculateTruePressure(Calibration calibration, int uncompensatedTemp, int uncompensatedPressure) {
        double b6 = b5(calibration, uncompensatedTemp);
        double x1 = (calibration.getB2() * (b6 * b6 / 4096)) / 2048;
        double x2 = calibration.getAC2() * b6 / 2048;
        double x3 = x1 + x2;
        
        return 0.0;
    }

    private double b5(Calibration calibration, int uncompensatedTemp) {
        double x1 = (uncompensatedTemp - calibration.getAC6()) * calibration.getAC5() / 32768.0;
        double x2 = calibration.getMC() * 2048.0 / (x1 + calibration.getMD());
        return x1 + x2;
    }
}
