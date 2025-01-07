package dev.sergevas.iot.env.application.service.bmp180;

import dev.sergevas.iot.env.domain.bmp180.Calibration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Bmp180TrueReadingsCalculatorTest {

    private static Bmp180TrueReadingsCalculator calculator;
    private static Calibration calibration;
    private static int ut;
    private static int up;

    @BeforeAll
    static void setUp() {
        calculator = new Bmp180TrueReadingsCalculator();
        calibration = new Calibration()
                .ac2(-72)
                .ac3(-14383)
                .ac4(32741)
                .ac5(32757)
                .ac6(23153)
                .mc(-8711)
                .md(2868);
        ut = 27898;
        up = 23843;
    }

    @Test
    void calculateTrueTemperature() {
        assertEquals(15.0, round(calculator.calculateTrueTemperature(calibration, ut)));
    }

    @Test
    void calculateTruePressure() {
        assertEquals(69964.0, round(calculator.calculateTruePressure(calibration, ut, up)));
    }
}