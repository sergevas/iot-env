package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import dev.sergevas.iot.env.application.CRC8;

public class CrcValidator {

    private static CRC8 crc8 = new CRC8();

    public static boolean isReadingsValid(byte[] rawReadingsData) {
        boolean isValid = true;
        return isValid;
    }

    public static long calculateCrcChecksum(byte[] rawReadingsData) {
        crc8.update(rawReadingsData);
        return crc8.getValue();
    }
}
