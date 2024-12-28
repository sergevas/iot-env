package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import dev.sergevas.iot.env.application.port.out.HardwareException;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.port.out.Sht3xSpec;
import dev.sergevas.iot.env.application.service.Profiler;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorType;
import dev.sergevas.iot.env.domain.Sht3xReadings;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkiverse.jef.java.embedded.framework.linux.core.NativeIOException;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CInterface;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.SMBus;
import io.quarkiverse.jef.java.embedded.framework.runtime.i2c.I2C;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import static dev.sergevas.iot.env.adapter.out.i2c.I2cCommandWriter.writeCommand;

import static dev.sergevas.iot.env.domain.ErrorEvent.*;

@ApplicationScoped
@IfBuildProfile("prod")
public class Sht3xAdapter implements Sht3xSpec {

    public static final int COMMAND_SHT3X_SOFT_RESET = 0x30A2;
    public static final int COMMAND_SINGLE_SHOT_HIGH_REPEAT_WO_CLOCK_STRETCH = 0x2C06;
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

    @Loggable(logReturnVal = true)
    @Override
    public Sht3xReadings readTemperatureAndHumidity() {
        try {
            writeCommand(smBus, COMMAND_SINGLE_SHOT_HIGH_REPEAT_WO_CLOCK_STRETCH);
            Thread.sleep(2);
            smBus.readBlockData()
            I2CInterface i2CInterface = smBus.getInterface();
            i2CInterface.write(new ByteBuffer().putInt());
        } catch (Exception e) {
            Log.error("Unable to read sensor data", e);
            throw new SensorException(E_SHT3X_0001.getId(), SensorType.TEMP_HUMID, SensorName.SHT3x,
                    E_SHT3X_0001.getName(), e);
        }
        Log.debug(Profiler.getCurrentMsg("Sht3xAdapter.readBoth()",
                "readTemperatureAndHumidityComplete"));
        return null;
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
}
