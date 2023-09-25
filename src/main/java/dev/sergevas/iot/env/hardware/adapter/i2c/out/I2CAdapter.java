package dev.sergevas.iot.env.hardware.adapter.i2c.out;

import dev.sergevas.iot.env.EnvDeviceAppConfig;
import dev.sergevas.iot.env.hardware.adapter.HardwareException;
import dev.sergevas.iot.env.hardware.port.out.I2C;
import io.quarkiverse.jef.java.embedded.framework.linux.core.NativeIOException;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;

public class I2CAdapter implements I2C {

    private final I2CBus i2CBus;

    public I2CAdapter(EnvDeviceAppConfig config) {
        try {
            this.i2CBus = I2CBus.create(config.getJefI2cPath());
            this.i2CBus.setTenBits(config.isJefI2cTenBits());
            this.i2CBus.setRetries(config.getJefI2cRetries());
            this.i2CBus.setTimeout(config.getJefI2cTimeout());
        } catch (NativeIOException e) {
            throw new HardwareException("Unable to init I2C bus", e);
        }
    }

    @Override
    public I2CBus getI2CBus() {
        return i2CBus;
    }
}
