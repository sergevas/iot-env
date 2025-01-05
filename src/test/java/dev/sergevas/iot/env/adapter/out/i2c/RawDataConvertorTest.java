package dev.sergevas.iot.env.adapter.out.i2c;

import dev.sergevas.iot.env.application.service.shared.ServiceInternalError;
import org.junit.jupiter.api.Test;

import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toSignedIntFromWord;
import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toUnsignedIntFromWord;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class RawDataConvertorTest {

    @Test
    void testToUnsignedIntFromWord() {
        assertEquals(65525, toUnsignedIntFromWord(new byte[]{(byte) 0xFF, (byte) 0xF5}));
        assertEquals(65535, toUnsignedIntFromWord(new byte[]{(byte) 0xFF, (byte) 0xFF}));
        assertEquals(32768, toUnsignedIntFromWord(new byte[]{(byte) 0x80, (byte) 0x00}));
        assertEquals(0, toUnsignedIntFromWord(new byte[]{(byte) 0x00, (byte) 0x00}));
    }

    @Test
    void testToSignedIntFromWord() {
        assertEquals(0, toSignedIntFromWord(new byte[]{(byte) 0x00, (byte) 0x00}));
        assertEquals(-1, toSignedIntFromWord(new byte[]{(byte) 0xFF, (byte) 0xFF}));
        assertEquals(32767, toSignedIntFromWord(new byte[]{(byte) 0x7F, (byte) 0xFF}));
        assertEquals(-32768, toSignedIntFromWord(new byte[]{(byte) 0x80, (byte) 0x00}));
        assertEquals(-11, toSignedIntFromWord(new byte[]{(byte) 0xFF, (byte) 0xF5}));
        assertEquals(32757, toSignedIntFromWord(new byte[]{(byte) 0x7F, (byte) 0xF5}));
    }

    @Test
    void testToValidateWord() {
        assertEquals("Invalid raw data word [null]. Must not be null and have length of 2 bytes",
                assertThrowsExactly(ServiceInternalError.class, () -> toSignedIntFromWord(null)).getMessage());
        assertEquals("Invalid raw data word [[127, -11, -11]]. Must not be null and have length of 2 bytes",
                assertThrowsExactly(ServiceInternalError.class,
                        () -> toSignedIntFromWord(new byte[]{(byte) 0x7F, (byte) 0xF5, (byte) 0xF5})).getMessage());
    }
}
