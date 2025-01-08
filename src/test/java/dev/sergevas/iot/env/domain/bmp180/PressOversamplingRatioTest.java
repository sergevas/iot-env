package dev.sergevas.iot.env.domain.bmp180;

import org.junit.jupiter.api.Test;

import static dev.sergevas.iot.env.domain.bmp180.PressOversamplingRatio.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PressOversamplingRatioTest {

    @Test
    void testGetConversionTime() {
        assertEquals(4_500_000, SINGLE.getConversionTime());
        assertEquals(7_500_000, TWO_TIMES.getConversionTime());
        assertEquals(13_500_000, FOUR_TIMES.getConversionTime());
        assertEquals(25_500_000, EIGHT_TIMES.getConversionTime());
    }
}
