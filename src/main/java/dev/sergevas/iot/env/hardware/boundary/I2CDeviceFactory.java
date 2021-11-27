package dev.sergevas.iot.env.hardware.boundary;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import dev.sergevas.iot.env.performance.controller.Profiler;
import dev.sergevas.iot.env.shared.controller.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class I2CDeviceFactory {

    private static final String CLASS_NAME = I2CDeviceFactory.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASS_NAME);

    private static Map<String, I2CDevice> i2CDeviceInstances = new HashMap<>();

    public static I2CBus i2CBus;

    public static I2CDevice getDeviceInstance(String instanceId, int i2CDeviceAddr) {
        LOG.log(Level.FINE, CLASS_NAME + "getDeviceInstance(" + instanceId + ", " + i2CDeviceAddr + ")");
        return Optional.ofNullable(i2CDeviceInstances.get(instanceId))
                .orElseGet(() -> {
                    try {
                        Profiler.init("I2CBusFactory.create()");
                        Optional.ofNullable(i2CBus).orElseGet(() -> {
                            i2CBus = I2CBusFactory.create();
                            LOG.log(Level.FINE, Profiler.getCurrentMsg("I2CBusFactory.create()", "I2CBusFactory.create()"));
                            return i2CBus;
                        });
                        I2CDevice i2cDevice = i2CBus.getDevice(i2CDeviceAddr);
                        LOG.log(Level.FINE, Profiler.getCurrentMsg("I2CDeviceFactory.create()", "I2CBus.getDevice()"));
                        i2CDeviceInstances.put(instanceId, i2cDevice);
                        return i2cDevice;
                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
                        throw new HardwareException("Unable to create I2C device", e);
                    }
                });
    }
}
