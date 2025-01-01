package dev.sergevas.iot.env.application.service.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CRC8Test {

    @Test
    void calculateCrcChecksum() {
        assertEquals(0x92, CRC8.calculateCrcChecksum(new byte[]{(byte) 0xBE, (byte) 0xEF}));
        assertEquals(0x7A, CRC8.calculateCrcChecksum(new byte[]{(byte) 0x67, (byte) 0x00}));
        assertEquals(0xC2, CRC8.calculateCrcChecksum(new byte[]{(byte) 0x5F, (byte) 0x40}));
    }
}
