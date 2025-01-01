package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CrcValidatorTest {

    @Test
    void isReadingsValid() {
        assertTrue(CrcValidator.isReadingsValid(new byte[]{(byte) 0xBE, (byte) 0xEF}, (byte) 0x92));
        assertFalse(CrcValidator.isReadingsValid(new byte[]{(byte) 0xBE, (byte) 0xEE}, (byte) 0x92));
        assertTrue(CrcValidator.isReadingsValid(new byte[]{(byte) 0x67, (byte) 0x00}, (byte) 0x7A));
        assertTrue(CrcValidator.isReadingsValid(new byte[]{(byte) 0x5F, (byte) 0x40}, (byte) 0xC2));
    }
}
