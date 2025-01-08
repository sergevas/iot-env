package dev.sergevas.iot.env.domain.bmp180;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutRegisterTest {

    @Test
    void toUT() {
        assertEquals(0, new OutRegister().msb(0).lsb(0).toUT());
        assertEquals(-1, new OutRegister().msb(0xFF).lsb(0xFF).toUT());
        assertEquals(32767, new OutRegister().msb(0x7F).lsb(0xFF).toUT());
        assertEquals(-32768, new OutRegister().msb(0x80).lsb(0x00).toUT());
        assertEquals(-11, new OutRegister().msb(0xFF).lsb(0xF5).toUT());
        assertEquals(32757, new OutRegister().msb(0x7F).lsb(0xF5).toUT());
    }

    @Test
    void toUP() {
        assertEquals(0, new OutRegister().msb(0).lsb(0).xlsb(0)
                .pressOversamplingRatio(PressOversamplingRatio.SINGLE).toUP());

        assertEquals(23843, new OutRegister().msb(0x5D).lsb(0x23).xlsb(0x7F)
                .pressOversamplingRatio(PressOversamplingRatio.SINGLE).toUP());
        assertEquals(47686, new OutRegister().msb(0x5D).lsb(0x23).xlsb(0x7F)
                .pressOversamplingRatio(PressOversamplingRatio.TWO_TIMES).toUP());
        assertEquals(95373, new OutRegister().msb(0x5D).lsb(0x23).xlsb(0x7F)
                .pressOversamplingRatio(PressOversamplingRatio.FOUR_TIMES).toUP());
        assertEquals(190747, new OutRegister().msb(0x5D).lsb(0x23).xlsb(0x7F)
                .pressOversamplingRatio(PressOversamplingRatio.EIGHT_TIMES).toUP());
    }
}
