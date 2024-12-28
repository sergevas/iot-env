package dev.sergevas.iot.env.adapter.out.i2c;

import io.quarkiverse.jef.java.embedded.framework.linux.i2c.SMBus;

import java.io.IOException;

public class I2cCommandWriter {

    public static void writeCommand(final SMBus smbus, final int command) throws IOException {
        var commandMsb = command >> 8;
        var commandLsb = command & 0xFF;
        smbus.writeByte(commandMsb);
        smbus.writeByte(commandLsb);
    }
}
