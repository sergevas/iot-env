package dev.sergevas.iot.env.adapter.out.i2c;

import dev.sergevas.iot.env.application.port.out.HardwareException;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CInterface;

import java.io.IOException;

public class I2CInterfaceProvider {

    public static I2CInterface i2C(final int deviceAddress, final I2CBus i2C0Bus) {
        I2CInterface i2CInterface;
        try {
            i2C0Bus.selectSlave(deviceAddress, true);
            i2CInterface = i2C0Bus.select(deviceAddress);
        } catch (IOException e) {
            throw new HardwareException(e);
        }
        return i2CInterface;
    }
}
