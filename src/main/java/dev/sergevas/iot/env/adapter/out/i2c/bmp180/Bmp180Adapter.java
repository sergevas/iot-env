package dev.sergevas.iot.env.adapter.out.i2c.bmp180;

import dev.sergevas.iot.env.application.port.out.BMP180Spec;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.service.shared.StringUtil;
import dev.sergevas.iot.env.domain.bmp180.Calibration;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.runtime.i2c.I2C;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static dev.sergevas.iot.env.adapter.out.i2c.I2CInterfaceProvider.i2C;
import static dev.sergevas.iot.env.adapter.out.i2c.I2cCommandWriter.writeCommand;
import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toSignedIntFromWord;
import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toUnsignedIntFromWord;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_BMP180_0001;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_BMP180_0002;
import static dev.sergevas.iot.env.domain.SensorName.BMP180;
import static dev.sergevas.iot.env.domain.SensorType.TEMP_PRESS;
import static dev.sergevas.iot.env.domain.bmp180.Calibration.CalibrationRegister.*;
import static java.lang.Thread.sleep;

@IfBuildProfile("prod")
@ApplicationScoped
public class Bmp180Adapter implements BMP180Spec {

    private static final int CHIP_ID_REGISTER = 0xD0;
    private static final int SOFT_RESET_REGISTER = 0xE0;
    private static final int CONTROL_REGISTER = 0xF4;

    @I2C(name = "i2c0")
    I2CBus i2C0Bus;

    @ConfigProperty(name = "bmp180.moduleAddress")
    int bmp180ModuleAddress;

    private Calibration calibration;
    private String chipId;

    @Loggable
    @PostConstruct
    public void init() {
        calibration = readCalibration();
        chipId = readChipId();
    }

    @Loggable(logReturnVal = true)
    @Override
    public Calibration readCalibration() {
        try {
            var i2CInterface = i2C(bmp180ModuleAddress, i2C0Bus);
            sleep(2);
            var smBus = i2CInterface.getSmBus();
            return new Calibration()
                    .ac1(toSignedIntFromWord(smBus.readByteData(AC1_MSB.address()), smBus.readByteData(AC1_LSB.address())))
                    .ac2(toSignedIntFromWord(smBus.readByteData(AC2_MSB.address()), smBus.readByteData(AC2_LSB.address())))
                    .ac3(toSignedIntFromWord(smBus.readByteData(AC3_MSB.address()), smBus.readByteData(AC3_LSB.address())))
                    .ac4(toUnsignedIntFromWord(smBus.readByteData(AC4_MSB.address()), smBus.readByteData(AC4_LSB.address())))
                    .ac5(toUnsignedIntFromWord(smBus.readByteData(AC5_MSB.address()), smBus.readByteData(AC5_LSB.address())))
                    .ac6(toUnsignedIntFromWord(smBus.readByteData(AC6_MSB.address()), smBus.readByteData(AC6_LSB.address())))
                    .b1(toSignedIntFromWord(smBus.readByteData(B1_MSB.address()), smBus.readByteData(B1_LSB.address())))
                    .b2(toSignedIntFromWord(smBus.readByteData(B2_MSB.address()), smBus.readByteData(B2_LSB.address())))
                    .mb(toSignedIntFromWord(smBus.readByteData(MB_MSB.address()), smBus.readByteData(MB_LSB.address())))
                    .mc(toSignedIntFromWord(smBus.readByteData(MC_MSB.address()), smBus.readByteData(MC_LSB.address())))
                    .md(toSignedIntFromWord(smBus.readByteData(MD_MSB.address()), smBus.readByteData(MD_LSB.address())));
        } catch (Exception e) {
            throw new SensorException(E_BMP180_0001.getId(), TEMP_PRESS, BMP180, E_BMP180_0001.getName(), e);
        }
    }

    @Loggable(logReturnVal = true)
    @Override
    public double readTemperature() {
        return 0;
    }

    @Loggable(logReturnVal = true)
    @Override
    public double readPressure() {
        return 0;
    }

    @Loggable(logReturnVal = true)
    @Override
    public String readChipId() {
        try {
            if (chipId == null) {
                return StringUtil.toHexString(i2C(bmp180ModuleAddress, i2C0Bus).getSmBus().readByteData(CHIP_ID_REGISTER));
            }
            return chipId;
        } catch (Exception e) {
            throw new SensorException(E_BMP180_0001.getId(), TEMP_PRESS, BMP180, E_BMP180_0001.getName(), e);
        }
    }

    @Loggable
    @Override
    public void softReset() {
        try {
            writeCommand(i2C(bmp180ModuleAddress, i2C0Bus), SOFT_RESET_REGISTER);
        } catch (Exception e) {
            throw new SensorException(E_BMP180_0002.getId(), TEMP_PRESS, BMP180, E_BMP180_0002.getName(), e);
        }
    }
}
