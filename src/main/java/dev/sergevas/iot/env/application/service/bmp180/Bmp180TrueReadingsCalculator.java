package dev.sergevas.iot.env.application.service.bmp180;

import dev.sergevas.iot.env.domain.bmp180.Calibration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
        long p;

        final int oss = pressOversamplingRatioProvider.getRatio().getOss();
        final long b6 = b5(calibration, uncompensatedTemp) - 4000;
        System.out.println("b6 = " + b6);

        long x1 = (calibration.getB2() * ((b6 * b6) >> 12)) >> 11;
        System.out.println("x1 = " + x1);
        long x2 = (calibration.getAC2() * b6) >> 11;
        System.out.println("x2 = " + x2);
        long x3 = x1 + x2;
        System.out.println("x3 = " + x3);
        final long b3 = (((calibration.getAC1() * 4L + x3) << oss) + 2) / 4;
        System.out.println("b3 = " + b3);

        x1 = (calibration.getAC3() * b6) >> 13;
        System.out.println("x1 = " + x1);
        x2 = (calibration.getB1() * ((b6 * b6) >> 12)) >> 16;
        System.out.println("x2 = " + x2);
        x3 = ((x1 + x2) + 2) >> 2;
        System.out.println("x3 = " + x3);
        final long b4 = (calibration.getAC4() * (x3 + 32768)) >> 15;
        System.out.println("b4 = " + b4);

        final long b7 = (uncompensatedPressure - b3) * (50_000 >> oss);
        System.out.println("b7 = " + b7);

        p = b7 < 0x80_000_000L ? (b7 << 1) / b4 : (b7 / b4) << 1;
        System.out.println("p = " + p);

        x1 = p >> 8;
        x1 *= x1;
        System.out.println("x1 = " + x1);
        x1 = (x1 * 3038) >> 16;
        System.out.println("x1 = " + x1);
        x2 = (-7357 * p) >> 16;
        System.out.println("x2 = " + x2);
        p += (x1 + x2 + 3791) >> 4; // pressure in Pa
        return p;
    }
}
