package dev.sergevas.iot.env.adapter.out.i2c.bmp180;

import dev.sergevas.iot.env.adapter.out.i2c.I2CInterfaceProvider;
import dev.sergevas.iot.env.application.port.out.BMP180Spec;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.service.bmp180.PressOversamplingRatioProvider;
import dev.sergevas.iot.env.application.service.shared.StringUtil;
import dev.sergevas.iot.env.domain.bmp180.Calibration;
import dev.sergevas.iot.env.domain.bmp180.OutRegister;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CInterface;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.SMBus;
import io.quarkiverse.jef.java.embedded.framework.runtime.i2c.I2C;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;

import static dev.sergevas.iot.env.adapter.out.i2c.I2cCommandWriter.writeCommand;
import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toSignedIntFromWord;
import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toUnsignedIntFromWord;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_BMP180_0001;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_BMP180_0002;
import static dev.sergevas.iot.env.domain.SensorName.BMP180;
import static dev.sergevas.iot.env.domain.SensorType.TEMP_PRESS;
import static dev.sergevas.iot.env.domain.bmp180.Calibration.CalibrationRegister.*;
import static dev.sergevas.iot.env.domain.bmp180.OutRegister.OutRegisterAddress.*;
import static dev.sergevas.iot.env.domain.bmp180.StartOfConversation.IN_PROGRESS;
import static java.lang.Thread.sleep;

@IfBuildProfile("prod")
@ApplicationScoped
public class Bmp180Adapter implements BMP180Spec {

    private static final int CHIP_ID_REGISTER = 0xD0;
    private static final int SOFT_RESET_REGISTER = 0xE0;
    private static final int CONTROL_REGISTER = 0xF4;
    private static final int CONTROL_REGISTER_TEMP_MEASUREMENT_COMMAND = 0x2E;
    private static final int CONTROL_REGISTER_PRESS_MEASUREMENT_DEFAULT_COMMAND = 0x34;
    //  BST-BMP180-DS000-12 Table 8
    private static final long TEMP_MEASUREMENT_MAX_CONVERSION_TIME = 4_500_000;

    @I2C(name = "i2c0")
    I2CBus i2C0Bus;

    @ConfigProperty(name = "bmp180.moduleAddress")
    int bmp180ModuleAddress;

    @Inject
    PressOversamplingRatioProvider pressOversamplingRatioProvider;

    private int controlRegPressMeasurementCommand;

    @Loggable
    @PostConstruct
    public void init() {
        controlRegPressMeasurementCommand = CONTROL_REGISTER_PRESS_MEASUREMENT_DEFAULT_COMMAND
                | pressOversamplingRatioProvider.getRatio().getOss() << 6;
        Log.infof("controlRegPressMeasurementCommand=%s", StringUtil.toHexString(controlRegPressMeasurementCommand));
    }

    @Loggable(logReturnVal = true)
    @Override
    public Calibration readCalibration() {
        try {
            var smBus = smBus();
            sleep(2);
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
    public int readUncompensatedTemperature() {
        try {
            var smBus = smBus();
            smBus.writeByteData(CONTROL_REGISTER, CONTROL_REGISTER_TEMP_MEASUREMENT_COMMAND);
            sleep(Duration.ofNanos(TEMP_MEASUREMENT_MAX_CONVERSION_TIME));
            return new OutRegister()
                    .msb(smBus.readByteData(OUT_MSB.address()))
                    .lsb(smBus.readByteData(OUT_LSB.address()))
                    .toUT();
        } catch (Exception e) {
            throw new SensorException(E_BMP180_0001.getId(), TEMP_PRESS, BMP180, E_BMP180_0001.getName(), e);
        }
    }

    @Loggable(logReturnVal = true)
    @Override
    public int readUncompensatedPressure() {
        try {
            var smBus = smBus();
            smBus.writeByteData(CONTROL_REGISTER, controlRegPressMeasurementCommand);
            while (isPressureMeasurementInProgress(smBus)) {
                sleep(Duration.ofNanos(pressOversamplingRatioProvider.getRatio().getConversionTime()));
            }
            return new OutRegister()
                    .msb(smBus.readByteData(OUT_MSB.address()))
                    .lsb(smBus.readByteData(OUT_LSB.address()))
                    .xlsb(smBus.readByteData(OUT_XLSB.address()))
                    .pressOversamplingRatio(pressOversamplingRatioProvider.getRatio())
                    .toUP();
        } catch (Exception e) {
            throw new SensorException(E_BMP180_0001.getId(), TEMP_PRESS, BMP180, E_BMP180_0001.getName(), e);
        }
    }

    private boolean isPressureMeasurementInProgress(SMBus smBus) throws Exception {
        int controlRegisterCurrState = smBus.readByteData(CONTROL_REGISTER);
        Log.debugf("controlRegisterCurrState=%s", StringUtil.toHexString(controlRegisterCurrState));
        boolean isPressureMeasurementInProgress = IN_PROGRESS.getSco() == (controlRegisterCurrState & IN_PROGRESS.getSco());
        Log.debugf("isPressureMeasurementInProgress=%b", isPressureMeasurementInProgress);
        return isPressureMeasurementInProgress;
    }

    @Loggable(logReturnVal = true)
    @Override
    public String readChipId() {
        try {
            return StringUtil.toHexString(i2C().getSmBus().readByteData(CHIP_ID_REGISTER));
        } catch (Exception e) {
            throw new SensorException(E_BMP180_0001.getId(), TEMP_PRESS, BMP180, E_BMP180_0001.getName(), e);
        }
    }

    @Loggable
    @Override
    public void softReset() {
        try {
            writeCommand(i2C(), SOFT_RESET_REGISTER);
        } catch (Exception e) {
            throw new SensorException(E_BMP180_0002.getId(), TEMP_PRESS, BMP180, E_BMP180_0002.getName(), e);
        }
    }

    private I2CInterface i2C() {
        return I2CInterfaceProvider.i2C(bmp180ModuleAddress, i2C0Bus);
    }

    private SMBus smBus() {
        return I2CInterfaceProvider.smBus(bmp180ModuleAddress, i2C0Bus);
    }
}
