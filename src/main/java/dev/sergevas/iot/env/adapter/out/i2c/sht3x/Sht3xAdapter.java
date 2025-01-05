package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import dev.sergevas.iot.env.adapter.out.i2c.I2cDataReader;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.port.out.Sht3xSpec;
import dev.sergevas.iot.env.application.service.shared.StringUtil;
import dev.sergevas.iot.env.domain.sht3x.HeaterState;
import dev.sergevas.iot.env.domain.sht3x.Sht3xReadings;
import dev.sergevas.iot.env.domain.sht3x.StatusRegister;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.runtime.i2c.I2C;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static dev.sergevas.iot.env.adapter.out.i2c.I2CInterfaceProvider.i2C;
import static dev.sergevas.iot.env.adapter.out.i2c.I2cCommandWriter.writeCommand;
import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toUnsignedIntFromWord;
import static dev.sergevas.iot.env.adapter.out.i2c.sht3x.CrcValidator.warnIfNotValid;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_SHT3X_0001;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_SHT3X_0002;
import static dev.sergevas.iot.env.domain.SensorName.SHT3x;
import static dev.sergevas.iot.env.domain.SensorType.TEMP_HUMID;
import static java.lang.Thread.sleep;
import static java.util.Arrays.copyOfRange;

@ApplicationScoped
@IfBuildProfile("prod")
public class Sht3xAdapter implements Sht3xSpec {

    public static final int COMMAND_SHT3X_SOFT_RESET = 0x30A2;
    public static final int COMMAND_SINGLE_SHOT_HIGH_REPEAT_WITH_CLOCK_STRETCH = 0x2C06;
    public static final int COMMAND_SINGLE_SHOT_HIGH_REPEAT_WO_CLOCK_STRETCH = 0x2400;
    public static final int COMMAND_HEATER_ENABLE = 0x306D;
    public static final int COMMAND_HEATER_DISABLE = 0x3066;
    public static final int COMMAND_READ_STATUS_REGISTER = 0xF32D;
    public static final int COMMAND_CLEAR_STATUS_REGISTER = 0x3041;
    public static final int SHT3X_READINGS_DATA_LENGTH = 6;
    public static final int SHT3X_STATUS_DATA_LENGTH = 3;

    @I2C(name = "i2c0")
    I2CBus i2C0Bus;

    @ConfigProperty(name = "sht3x.moduleAddress")
    int sht3xModuleAddress;

    @Loggable
    @Override
    public void softReset() {
        try {
            writeCommand(i2C(sht3xModuleAddress, i2C0Bus), COMMAND_SHT3X_SOFT_RESET);
        } catch (Exception e) {
            throw new SensorException(E_SHT3X_0002.getId(), TEMP_HUMID, SHT3x, E_SHT3X_0002.getName(), e);
        }
    }

    @Loggable(logReturnVal = true)
    @Override
    public Sht3xReadings readTemperatureAndHumidity() {
        try {
            sleep(2);
            var i2CInterface = i2C(sht3xModuleAddress, i2C0Bus);
            writeCommand(i2CInterface, COMMAND_SINGLE_SHOT_HIGH_REPEAT_WITH_CLOCK_STRETCH);
            sleep(2);
            byte[] readings = I2cDataReader.readData(i2CInterface, SHT3X_READINGS_DATA_LENGTH);
            Log.infof("SHT3x raw readings: %s", StringUtil.toHexString(readings));
            return toSht3xReadings(readings);
        } catch (Exception e) {
            throw new SensorException(E_SHT3X_0001.getId(), TEMP_HUMID, SHT3x, E_SHT3X_0001.getName(), e);
        }
    }

    @Loggable(logReturnVal = true)
    @Override
    public StatusRegister readStatus() {
        try {
            var i2CInterface = i2C(sht3xModuleAddress, i2C0Bus);
            writeCommand(i2CInterface, COMMAND_READ_STATUS_REGISTER);
            sleep(2);
            byte[] status = I2cDataReader.readData(i2CInterface, SHT3X_STATUS_DATA_LENGTH);
            Log.infof("SHT3x raw status: %s", StringUtil.toHexString(status));
            warnIfNotValid(copyOfRange(status, 0, 2), status[2]);
            return StatusRegister.fromRawData(status);
        } catch (Exception e) {
            throw new SensorException(E_SHT3X_0001.getId(), TEMP_HUMID, SHT3x, E_SHT3X_0001.getName(), e);
        }
    }

    @Loggable(logReturnVal = true)
    @Override
    public StatusRegister clearStatus() {
        try {
            var i2CInterface = i2C(sht3xModuleAddress, i2C0Bus);
            writeCommand(i2CInterface, COMMAND_CLEAR_STATUS_REGISTER);
            sleep(2);
            return readStatus();
        } catch (Exception e) {
            throw new SensorException(E_SHT3X_0002.getId(), TEMP_HUMID, SHT3x, E_SHT3X_0002.getName(), e);
        }
    }

    public Sht3xReadings toSht3xReadings(byte[] readings) {
        byte[] tempReadings = copyOfRange(readings, 0, 2);
        warnIfNotValid(tempReadings, readings[2]);
        byte[] humidReadings = copyOfRange(readings, 3, 5);
        warnIfNotValid(humidReadings, readings[5]);
        return new Sht3xReadings(
                175.0 * toUnsignedIntFromWord(tempReadings) / 65535 - 45,
                100.0 * toUnsignedIntFromWord(humidReadings) / 65535
        );
    }

    @Loggable(logArguments = true, logReturnVal = true)
    @Override
    public boolean updateHeater(HeaterState heaterState) {
        try {
            boolean result = true;
            switch (heaterState) {
                case ON -> writeCommand(i2C(sht3xModuleAddress, i2C0Bus), COMMAND_HEATER_ENABLE);
                case OFF -> writeCommand(i2C(sht3xModuleAddress, i2C0Bus), COMMAND_HEATER_DISABLE);
                default -> result = false;
            }
            return result;
        } catch (Exception e) {
            throw new SensorException(E_SHT3X_0002.getId(), TEMP_HUMID, SHT3x, E_SHT3X_0002.getName(), e);
        }
    }
}
