package dev.sergevas.iot.env.application.port.out;

import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;

public interface I2C {

    I2CBus getI2CBus();
}
