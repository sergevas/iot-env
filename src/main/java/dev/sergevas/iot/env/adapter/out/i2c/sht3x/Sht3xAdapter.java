package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import dev.sergevas.iot.env.adapter.out.i2c.I2cDataReader;
import dev.sergevas.iot.env.application.port.out.HardwareException;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.port.out.Sht3xSpec;
import dev.sergevas.iot.env.application.service.StringUtil;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorType;
import dev.sergevas.iot.env.domain.sht3x.HeaterState;
import dev.sergevas.iot.env.domain.sht3x.Sht3xReadings;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkiverse.jef.java.embedded.framework.linux.core.NativeIOException;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.SMBus;
import io.quarkiverse.jef.java.embedded.framework.runtime.i2c.I2C;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static dev.sergevas.iot.env.adapter.out.i2c.I2cCommandWriter.writeCommand;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_SHT3X_0001;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_SHT3X_0002;

@ApplicationScoped
@IfBuildProfile("prod")
public class Sht3xAdapter implements Sht3xSpec {

    public static final int COMMAND_SHT3X_SOFT_RESET = 0x30A2;
    public static final int COMMAND_SINGLE_SHOT_HIGH_REPEAT_WITH_CLOCK_STRETCH = 0x2C06;
    public static final int COMMAND_HEATER_ENABLE = 0x306D;
    public static final int COMMAND_HEATER_DISABLE = 0x3066;
    public static final int SHT3X_READINGS_DATA_LENGTH = 6;

    @I2C(name = "i2c0")
    I2CBus i2C0Bus;

    @ConfigProperty(name = "sht3x.moduleAddress")
    int sht3xModuleAddress;

    private SMBus smBus;

    @PostConstruct
    public void initSMBus() {
        try {
            smBus = i2C0Bus.select(sht3xModuleAddress).getSmBus();
            softReset();
        } catch (NativeIOException e) {
            Log.error(e);
            throw new HardwareException(e);
        }
    }

    @Loggable
    @Override
    public void softReset() {
        try {
            writeCommand(smBus, COMMAND_SHT3X_SOFT_RESET);
        } catch (Exception e) {
            throw new SensorException(E_SHT3X_0002.getId(), SensorType.TEMP_HUMID, SensorName.SHT3x,
                    E_SHT3X_0002.getName(), e);
        }
    }

    @Loggable(logReturnVal = true)
    @Override
    public Sht3xReadings readTemperatureAndHumidity() {
        try {
            writeCommand(smBus, COMMAND_SINGLE_SHOT_HIGH_REPEAT_WITH_CLOCK_STRETCH);
            Thread.sleep(2);
            int[] readings = I2cDataReader.readData(smBus, SHT3X_READINGS_DATA_LENGTH);
            Log.infof("SHT3x row readings: %s", StringUtil.toHexString(readings));
            return toSht3xReadings(readings);
        } catch (Exception e) {
            throw new SensorException(E_SHT3X_0001.getId(), SensorType.TEMP_HUMID, SensorName.SHT3x,
                    E_SHT3X_0001.getName(), e);
        }
    }

    public Sht3xReadings toSht3xReadings(int[] readings) {
        int rawTemperature = readings[0] << 8 | readings[1];
        int rawHumidity = readings[3] << 8 | readings[4];
        return new Sht3xReadings(
                175.0 * rawTemperature / 65535 - 45,
                100.0 * rawHumidity / 65535
        );
    }

    @Loggable(logArguments = true, logReturnVal = true)
    @Override
    public boolean updateHeater(HeaterState heaterState) {
        try {
            boolean result = true;
            switch (heaterState) {
                case ON -> writeCommand(smBus, COMMAND_HEATER_ENABLE);
                case OFF -> writeCommand(smBus, COMMAND_HEATER_DISABLE);
                default -> result = false;
            }
            return result;
        } catch (Exception e) {
            throw new SensorException(E_SHT3X_0002.getId(), SensorType.TEMP_HUMID, SensorName.SHT3x,
                    E_SHT3X_0002.getName(), e);
        }
    }
}
