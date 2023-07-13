package dev.sergevas.iot.env.hardware.adapter;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import dev.sergevas.iot.env.shared.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

public class I2CBusProvider implements InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(I2CBusProvider.class);

    private I2CBus i2CBus;

    public I2CBus getI2CBus() {
        return i2CBus;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            i2CBus = I2CFactory.getInstance(I2CBus.BUS_0);
        } catch (IOException | I2CFactory.UnsupportedBusNumberException e) {
            LOG.error("Unable to create Pi4J I2CBus instance {}", ExceptionUtils.getStackTrace(e));
            throw new HardwareException("Unable to create Pi4J I2CBus instance", e);
        }
    }

    @Override
    public void destroy() {
        LOG.info("Close I2CBus instance start...");
        try {
            if (i2CBus != null) {
                i2CBus.close();
            }
        } catch (Exception e) {
            LOG.error("Unable to close Pi4J I2CBus instance {}", ExceptionUtils.getStackTrace(e));
            throw new HardwareException("Unable to close Pi4J I2CBus instance", e);
        }
        LOG.info("Close I2CBus instance complete...");
    }
}
