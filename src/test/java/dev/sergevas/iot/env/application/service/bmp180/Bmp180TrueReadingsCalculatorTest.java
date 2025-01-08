package dev.sergevas.iot.env.application.service.bmp180;

import dev.sergevas.iot.env.domain.bmp180.Calibration;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class Bmp180TrueReadingsCalculatorTest {

    @Inject
    Bmp180TrueReadingsCalculator calculator;

    private static Calibration calibration;
    private static int ut;
    private static int up;

    @BeforeAll
    static void setUp() {
        calibration = new Calibration()
                .ac1(408)
                .ac2(-72)
                .ac3(-14383)
                .ac4(32741)
                .ac5(32757)
                .ac6(23153)
                .b1(6190)
                .b2(4)
                .mb(-32768)
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
