package dev.sergevas.iot.env.adapter.out.i2c.bh1750;

import dev.sergevas.iot.env.EnvDeviceAppConfig;
import dev.sergevas.iot.env.application.port.out.LightIntensity;
import dev.sergevas.iot.env.application.port.out.HardwareException;
import dev.sergevas.iot.env.application.service.Profiler;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.service.HexStringUtil;
import dev.sergevas.iot.env.domain.ErrorEvent;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorType;
import io.quarkiverse.jef.java.embedded.framework.linux.core.NativeIOException;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CInterface;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.SMBus;

import java.nio.ByteBuffer;

public class Bh1750Adapter implements LightIntensity {

    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE = (byte) 0x20;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    private final SMBus smBus;

    public Bh1750Adapter(I2CBus i2CBus, EnvDeviceAppConfig config) {
        try {
            smBus = i2CBus.select(config.getBh1750ModuleAddress()).getSmBus();
        } catch (NativeIOException e) {
            throw new HardwareException(String.format("Unable to select I2C address [%d]", config.getBh1750ModuleAddress()));
        }
    }

    public double getLightIntensity() {
        double lightIntensity;
        try {
            Profiler.init("Bh1750Adapter.getLightIntensity()");
            smBus.writeByte(GY_302_BH1750_POWER_ON);
            smBus.writeByte(GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE);
            Thread.sleep(180); // H-Resolution Mode max measurement time
            System.out.println("Reading data from GY-302 BH1750...");
            byte[] readings = new byte[GY_302_BH1750_READINGS_DATA_LENGTH];
            I2CInterface i2CInterface = smBus.getInterface();
            i2CInterface.read(ByteBuffer.wrap(readings), GY_302_BH1750_READINGS_DATA_LENGTH);
            System.out.printf("GY-302 BH1750 readings: %s%n", HexStringUtil.toHexString(readings));
            smBus.writeByte(GY_302_BH1750_POWER_DOWN);
            lightIntensity = fromRawReadingsToLightIntensity(readings);
        } catch (Exception e) {
            System.err.println("Unable to get light intensity " + e);
            throw new SensorException(ErrorEvent.E_BH1750_0001.getId(), SensorType.LIGHT, SensorName.BH1750,
                    ErrorEvent.E_BH1750_0001.getName(), e);
        }
        System.out.println(Profiler.getCurrentMsg("Bh1750Adapter.getLightIntensity()", "getLightIntensityComplete"));
        return lightIntensity;
    }

    public double fromRawReadingsToLightIntensity(byte[] i2cReadings) {
        Profiler.init("Bh1750Adapter.fromRawReadingsToLightIntensity()");
        double lightIntensity = Math.round((Byte.toUnsignedInt(i2cReadings[0]) << 8
                | Byte.toUnsignedInt(i2cReadings[1])) / 1.2 * 100.0) / 100.0;
        System.out.printf("Light intensity: %f lux%n", lightIntensity);
        System.out.println(Profiler.getCurrentMsg("Bh1750Adapter.fromRawReadingsToLightIntensity()",
                "fromRawReadingsToLightIntensityComplete"));
        return lightIntensity;
    }
}
