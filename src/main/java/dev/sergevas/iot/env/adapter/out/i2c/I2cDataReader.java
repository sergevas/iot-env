package dev.sergevas.iot.env.adapter.out.i2c;

import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CInterface;

import java.io.IOException;
import java.nio.ByteBuffer;

public class I2cDataReader {

    public static byte[] readData(final I2CInterface i2C, final int length) throws IOException {
        byte[] readData = new byte[length];
        i2C.read(ByteBuffer.wrap(readData));
        return readData;
    }
}
