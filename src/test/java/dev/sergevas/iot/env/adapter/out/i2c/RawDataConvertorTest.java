package dev.sergevas.iot.env.adapter.out.i2c;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RawDataConvertorTest {

    @Test
    void testToUnsignedInt() {
        assertEquals(65525, RawDataConvertor.toUnsignedInt(new byte[]{(byte) 0xFF, (byte) 0xF5}));
    }
}
