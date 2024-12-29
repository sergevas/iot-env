package dev.sergevas.iot.env.adapter.out.i2c;

import io.quarkiverse.jef.java.embedded.framework.linux.i2c.SMBus;

import java.io.IOException;

public class I2cDataReader {

    public static int[] readData(final SMBus smbus, final int length) throws IOException {
        int[] readData = new int[length];
        for (int i = 0; i < length; i++) {
            readData[i] = smbus.readByte();
        }
        return readData;
    }
}
