package dev.sergevas.iot.env.hardware.boundary;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import dev.sergevas.iot.env.shared.controller.ExceptionUtils;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class I2CBusFactory {

    private static final Logger LOG = Logger.getLogger(I2CBusFactory.class.getName());

    private static I2CBus i2CBus;

    public static I2CBus create() {
        return Optional.ofNullable(i2CBus)
                .orElseGet(() -> {
                    try {
                        i2CBus = I2CFactory.getInstance(I2CBus.BUS_0);;
                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
                        throw new HardwareException("Unable to create a new I2CBus instance");
                    }
                    return i2CBus;
                });
    }

    public static void shutdown() {
        try {
            if (i2CBus != null) {
                i2CBus.close();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
            throw new HardwareException("Unable to close Pi4J I2CBus instance");
        }
    }


}