package dev.sergevas.iot.env.application.service.bmp180;

import dev.sergevas.iot.env.domain.bmp180.Calibration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import static java.lang.Math.pow;

@ApplicationScoped
public class Bmp180TrueReadingsCalculator {

    @Inject
    PressOversamplingRatioProvider pressOversamplingRatioProvider;

    private long b5(Calibration calibration, int uncompensatedTemp) {
        int x1 = (uncompensatedTemp - calibration.getAC6()) * calibration.getAC5() >> 15;
        System.out.println("x1 = " + x1);
        long x2 = Math.round(calibration.getMC() * 2048.0 / (x1 + calibration.getMD()));
        System.out.println("x2 = " + x2);
        long b5 = x1 + x2;
        System.out.println("b5 = " + b5);
        return b5;
    }

    public double calculateTrueTemperature(Calibration calibration, int uncompensatedTemp) {
        return (b5(calibration, uncompensatedTemp) + 8) / 160.0;
    }

    public double calculateTruePressure(Calibration calibration, int uncompensatedTemp, int uncompensatedPressure) {
        double pressure;
        final int oss = pressOversamplingRatioProvider.getRatio().getOss();
        final double b6 = b5(calibration, uncompensatedTemp) - 4000;
        System.out.println("b6 = " + b6);
        double x1 = (calibration.getB2() * (b6 * b6 / 4096)) / 2048;
        System.out.println("x1 = " + x1);
        double x2 = calibration.getAC2() * b6 / 2048;
        System.out.println("x2 = " + x2);
        double x3 = x1 + x2;
        System.out.println("x3 = " + x3);
        final double b3 = ((calibration.getAC1() * 4 + x3) * pow(2, oss) + 2) / 4;
        System.out.println("b3 = " + b3);
        x1 = calibration.getAC3() * b6 / 8192;
        System.out.println("x1 = " + x1);
        x2 = (calibration.getB1() * (b6 * b6 / 4096)) / 65536;
        System.out.println("x2 = " + x2);
        x3 = ((x1 + x2) + 2) / 4;
        System.out.println("x3 = " + x3);
        final double b4 = calibration.getAC4() * (x3 + 32768) / 32768;
        System.out.println("b4 = " + b4);
        final double b7 = (uncompensatedPressure - b3) * (50_000 / pow(2, oss));
        System.out.println("b7 = " + b7);
        pressure = b7 * 2 / b4;
        System.out.println("pressure = " + pressure);
        x1 = pow(pressure / 256, 2);
        System.out.println("x1 = " + x1);
        x1 = x1 * 3038 / 65536;
        System.out.println("x1 = " + x1);
        x2 = -7357 * pressure / 65536;
        System.out.println("x2 = " + x2);
        pressure += (x1 + x2 + 3791) / 16;
        return pressure;
    }

}
