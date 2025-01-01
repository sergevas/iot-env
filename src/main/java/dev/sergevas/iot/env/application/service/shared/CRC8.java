package dev.sergevas.iot.env.application.service.shared;

public class CRC8 {

    private static final int POLYNOMIAL = 0x31;

    public static long calculateCrcChecksum(byte[] rawReadingsData) {
        int crc = 0xFF;
        for (byte reading : rawReadingsData) {
            crc ^= reading & 0xFF;
            for (int i = 0; i < 8; i++) {
                crc = (crc & 0x80) != 0 ? ((crc << 1) ^ POLYNOMIAL) & 0xFF : (crc << 1) & 0xFF;
            }
        }
        return crc;
    }
}
