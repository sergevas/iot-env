package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import dev.sergevas.iot.env.application.service.shared.CRC8;
import io.quarkus.logging.Log;

import static dev.sergevas.iot.env.application.service.shared.StringUtil.toHexString;

public class CrcValidator {

    public static boolean isReadingsValid(byte[] rawReadings, byte crc) {
        return Byte.toUnsignedInt(crc) == CRC8.calculateCrcChecksum(rawReadings);
    }

    public static void warnIfNotValid(byte[] rawReadings, byte crc) {
        if (!isReadingsValid(rawReadings, crc)) {
            Log.warnf("Readings %s are not valid for CRC %s", toHexString(rawReadings), toHexString(crc));
        }
    }
}
