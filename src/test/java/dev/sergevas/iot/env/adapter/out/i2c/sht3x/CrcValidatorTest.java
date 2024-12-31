package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrcValidatorTest {

    @Test
    void isReadingsValid() {
    }

    @Test
    void calculateCrcChecksum() {
        assertEquals(0x92, CrcValidator.calculateCrcChecksum(new byte[]{(byte) 0xBE, (byte) 0xEF}));
    }
}