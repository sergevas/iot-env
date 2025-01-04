package dev.sergevas.iot.env.adapter.out.i2c.bmp180;

import dev.sergevas.iot.env.application.port.out.BMP180Spec;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Readings;
import dev.sergevas.iot.env.domain.bmp180.Calibration;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.runtime.i2c.I2C;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static dev.sergevas.iot.env.adapter.out.i2c.I2CInterfaceProvider.i2C;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_BMP180_0002;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_SHT3X_0001;
import static dev.sergevas.iot.env.domain.SensorName.BMP180;
import static dev.sergevas.iot.env.domain.SensorName.SHT3x;
import static dev.sergevas.iot.env.domain.SensorType.TEMP_HUMID;
import static dev.sergevas.iot.env.domain.SensorType.TEMP_PRESS;
import static java.lang.Thread.sleep;

@IfBuildProfile("prod")
@ApplicationScoped
public class Bmp180Adapter implements BMP180Spec {

    @I2C(name = "i2c0")
    I2CBus i2C0Bus;

    @ConfigProperty(name = "bmp180.moduleAddress")
    int bmp180ModuleAddress;

    private Calibration calibration;

    @Loggable
    @PostConstruct
    public void init() {
        calibration = readCalibration();
        if (calibration.isNotValid()) {
            throw new SensorException(E_BMP180_0002.getId(), TEMP_PRESS, BMP180, E_BMP180_0002.getName()
                    + String.format(" %s", calibration));
        }
    }

    @Loggable(logReturnVal = true)
    @Override
    public Calibration readCalibration() {
        try {
            var i2CInterface = i2C(bmp180ModuleAddress, i2C0Bus);
            sleep(2);
//            i2CInterface.
//            byte[] readings = I2cDataReader.readData(i2CInterface, SHT3X_READINGS_DATA_LENGTH);
//            Log.infof("SHT3x raw readings: %s", StringUtil.toHexString(readings));
            return null;
        } catch (Exception e) {
            throw new SensorException(E_SHT3X_0001.getId(), TEMP_HUMID, SHT3x, E_SHT3X_0001.getName(), e);
        }
    }

    @Loggable(logReturnVal = true)
    @Override
    public Bmp180Readings readTemperatureAndPressure() {
        return null;
    }
}
