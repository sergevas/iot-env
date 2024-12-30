package dev.sergevas.iot.env.adapter.out.i2c;

import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CInterface;

import java.io.IOException;
import java.nio.ByteBuffer;

public class I2cCommandWriter {

    public static void writeCommand(final I2CInterface i2CInterface, final int command) throws IOException {
        var commandMsb = command >> 8;
        var commandLsb = command & 0xFF;
        i2CInterface.write(ByteBuffer.wrap(new byte[]{(byte) commandMsb, (byte) commandLsb}));
    }

    public static void writeCommand(final I2CInterface i2CInterface, final byte command) throws IOException {
        i2CInterface.write(ByteBuffer.wrap(new byte[]{command}));
    }
}
