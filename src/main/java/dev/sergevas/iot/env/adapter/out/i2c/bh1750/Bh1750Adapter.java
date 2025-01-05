package dev.sergevas.iot.env.adapter.out.i2c.bh1750;

import dev.sergevas.iot.env.adapter.out.i2c.I2cDataReader;
import dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor;
import dev.sergevas.iot.env.application.port.out.LightIntensityReader;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.service.shared.StringUtil;
import dev.sergevas.iot.env.domain.ErrorEvent;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorType;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.runtime.i2c.I2C;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static dev.sergevas.iot.env.adapter.out.i2c.I2CInterfaceProvider.i2C;
import static dev.sergevas.iot.env.adapter.out.i2c.I2cCommandWriter.writeCommand;

@ApplicationScoped
@IfBuildProfile("prod")
public class Bh1750Adapter implements LightIntensityReader {

    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE = 0x20;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    @I2C(name = "i2c0")
    I2CBus i2C0Bus;

    @ConfigProperty(name = "bh1750.moduleAddress")
    int bh1750ModuleAddress;

    @Loggable(logReturnVal = true)
    @Override
    public double readLightIntensity() {
        double lightIntensity;
        try {
            var i2CInterface = i2C(bh1750ModuleAddress, i2C0Bus);
            writeCommand(i2CInterface, GY_302_BH1750_POWER_ON);
            writeCommand(i2CInterface, GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE);
            Thread.sleep(180); // H-Resolution Mode max measurement time
            byte[] readings = I2cDataReader.readData(i2CInterface, GY_302_BH1750_READINGS_DATA_LENGTH);
            Log.infof("GY-302 BH1750 readings: %s", StringUtil.toHexString(readings));
            writeCommand(i2CInterface, GY_302_BH1750_POWER_DOWN);
            lightIntensity = fromRawReadingsToLightIntensity(readings);
        } catch (Exception e) {
            throw new SensorException(ErrorEvent.E_BH1750_0001.getId(), SensorType.LIGHT, SensorName.BH1750,
                    ErrorEvent.E_BH1750_0001.getName(), e);
        }
        return lightIntensity;
    }

    public double fromRawReadingsToLightIntensity(byte[] i2cReadings) {
        return Math.round(RawDataConvertor.toUnsignedIntFromWord(i2cReadings) / 1.2 * 100.0) / 100.0;
    }
}
