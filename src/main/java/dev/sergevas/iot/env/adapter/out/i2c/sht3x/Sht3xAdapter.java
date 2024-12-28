package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

import dev.sergevas.iot.env.application.port.out.HardwareException;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.port.out.Sht3xSpec;
import dev.sergevas.iot.env.application.service.Profiler;
import dev.sergevas.iot.env.domain.ErrorEvent;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorType;
import dev.sergevas.iot.env.domain.Sht3xReadings;
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

@ApplicationScoped
@IfBuildProfile("prod")
public class Sht3xAdapter implements Sht3xSpec {

    public static final int SHT3X_SOFT_RESET = 0x30A2;

    @I2C(name = "i2c0")
    I2CBus i2C0Bus;

    @ConfigProperty(name = "sht3x.moduleAddress")
    int sht3xModuleAddress;

    private SMBus smBus;

    @PostConstruct
    public void initSMBus() {
        try {
            smBus = i2C0Bus.select(sht3xModuleAddress).getSmBus();
        } catch (NativeIOException e) {
            Log.error(e);
            throw new HardwareException(e);
        }
    }

    // TODO: implement
    @Override
    public Sht3xReadings readTemperatureAndHumidity() {
        try {
            Profiler.init("Sht3xAdapter.readBoth()");
            I2CInterface i2CInterface = smBus.getInterface();
//            i2CInterface.write(new ByteBuffer().putInt());
        } catch (Exception e) {
            Log.error("Unable to read sensor data", e);
            throw new SensorException(ErrorEvent.E_SHT3X_0001.getId(), SensorType.TEMP_HUMID, SensorName.SHT3x,
                    ErrorEvent.E_SHT3X_0001.getName(), e);
        }
        Log.debug(Profiler.getCurrentMsg("Sht3xAdapter.readBoth()",
                "readTemperatureAndHumidityComplete"));
        return null;
    }
}
