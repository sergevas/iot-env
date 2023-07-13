package dev.sergevas.iot.env.bh1750.boundary;

import com.pi4j.io.i2c.I2CDevice;
import dev.sergevas.iot.env.hardware.adapter.I2CBusProvider;
import dev.sergevas.iot.env.performance.control.Profiler;
import dev.sergevas.iot.env.shared.entity.ErrorEventId;
import dev.sergevas.iot.env.shared.entity.SensorType;
import dev.sergevas.iot.env.shared.exception.SensorException;
import dev.sergevas.iot.env.transform.control.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

public class Bh1750Adapter implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(Bh1750Adapter.class);

    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE = (byte) 0x20;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    private final I2CBusProvider i2CBusProvider;
    private final Integer bh1750ModuleAddress;
    private I2CDevice bh1750I2CDevice;

    public Bh1750Adapter(I2CBusProvider i2CBusProvider, Integer bh1750ModuleAddress) {
        super();
        this.i2CBusProvider = i2CBusProvider;
        this.bh1750ModuleAddress = bh1750ModuleAddress;
    }

    public double getLightIntensity() {
        double lightIntensity;
        try {
            Profiler.init("Bh1750Adapter.getLightIntensity()");
            bh1750I2CDevice.write(GY_302_BH1750_POWER_ON);
            bh1750I2CDevice.write(GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE);
            Thread.sleep(180); // H-Resolution Mode max measurement time
            LOG.debug("Reading data from GY-302 BH1750...");
            byte[] readings = new byte[GY_302_BH1750_READINGS_DATA_LENGTH];
            bh1750I2CDevice.read(readings, 0, GY_302_BH1750_READINGS_DATA_LENGTH);
            LOG.debug("GY-302 BH1750 readings: {}", StringUtil.toHexString(readings));
            bh1750I2CDevice.write(GY_302_BH1750_POWER_DOWN);
            lightIntensity = fromRawReadingsToLightIntensity(readings);
        } catch (Exception e) {
            LOG.error("Unable to get light intensity", e);
            throw new SensorException(ErrorEventId.E_BH1750_0001.getId(), SensorType.LIGHT, ErrorEventId.E_BH1750_0001.getName(), e);
        }
        LOG.debug(Profiler.getCurrentMsg("Bh1750Adapter.getLightIntensity()",
                "getLightIntensityComplete"));
        return lightIntensity;
    }

    public double fromRawReadingsToLightIntensity(byte[] i2cReadings) {
        Profiler.init("Bh1750Adapter.fromRawReadingsToLightIntensity()");
        double lightIntensity = Math.round((Byte.toUnsignedInt(i2cReadings[0]) << 8
                | Byte.toUnsignedInt(i2cReadings[1])) / 1.2 * 100.0) / 100.0;
        LOG.debug("Light intensity: {} lux", lightIntensity);
        LOG.debug(Profiler.getCurrentMsg("Bh1750Adapter.fromRawReadingsToLightIntensity()",
                "fromRawReadingsToLightIntensityComplete"));
        return lightIntensity;
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        LOG.info("Create I2C device for {} start...", Bh1750Adapter.class);
        bh1750I2CDevice = i2CBusProvider.getI2CBus().getDevice(bh1750ModuleAddress);
        LOG.info("Create I2C device for {} complete...", Bh1750Adapter.class);
    }
}
