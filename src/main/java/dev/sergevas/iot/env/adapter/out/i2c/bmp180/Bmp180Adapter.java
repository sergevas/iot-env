package dev.sergevas.iot.env.adapter.out.i2c.bmp180;

import dev.sergevas.iot.env.adapter.out.i2c.I2CInterfaceProvider;
import dev.sergevas.iot.env.application.port.out.BMP180Spec;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.service.shared.StringUtil;
import dev.sergevas.iot.env.domain.bmp180.Calibration;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
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
import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toSignedIntFromWord;
import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toUnsignedIntFromWord;
import static dev.sergevas.iot.env.adapter.out.i2c.bmp180.Bmp180Adapter.OutRegister.OUT_LSB;
import static dev.sergevas.iot.env.adapter.out.i2c.bmp180.Bmp180Adapter.OutRegister.OUT_MSB;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_BMP180_0001;
import static dev.sergevas.iot.env.domain.ErrorEvent.E_BMP180_0002;
import static dev.sergevas.iot.env.domain.SensorName.BMP180;
import static dev.sergevas.iot.env.domain.SensorType.TEMP_PRESS;
import static dev.sergevas.iot.env.domain.bmp180.Calibration.CalibrationRegister.*;
import static java.lang.Thread.sleep;

@IfBuildProfile("prod")
@ApplicationScoped
public class Bmp180Adapter implements BMP180Spec {

    /*  BST-BMP180-DS000-12 4. Global Memory Map
       | Register  | Address  |
       |  OUT_MSB  |   0xF6   |
       |  OUT_LSB  |   0xF7   |
       |  OUT_XLSB |   0xF8   |
   */

    public enum OutRegister {

        OUT_MSB(0xF6),
        OUT_LSB(0xF7),
        OUT_XLSB(0xF8);

        private final int address;

        OutRegister(int address) {
            this.address = address;
        }

        public int address() {
            return address;
        }
    }

    public enum PressOversamplingRatio {
        SINGLE(0x00),
        TWO_TIMES(0x40),
        FOUR_TIMES(0x80),
        EIGHT_TIMES(0xC0);

        private final int oss;

        PressOversamplingRatio(int oss) {
            this.oss = oss;
        }

        public int getOss() {
            return oss;
        }
    }

    public enum StartOfConversation {
        IN_PROGRESS(0x00000020),
        COMPLETE(0x00000000);

        private int sco;

        StartOfConversation(int sco) {
            this.sco = sco;
        }

        public int getSco() {
            return sco;
        }
    }

    private static final int CHIP_ID_REGISTER = 0xD0;
    private static final int SOFT_RESET_REGISTER = 0xE0;
    private static final int CONTROL_REGISTER = 0xF4;
    private static final int CONTROL_REGISTER_TEMP_MEASUREMENT_COMMAND = 0x2E;
    private static final int CONTROL_REGISTER_PRESS_MEASUREMENT_DEFAULT_COMMAND = 0x34;
    //  BST-BMP180-DS000-12 Table 8
    private static final long TEMP_MEASUREMENT_MAX_CONVERSION_TIME = 5;
    /*
     * BST-BMP180-DS000-12 Table 3, Table 8
     * Max conversion time for Ultra Low Power Mode (oversampling setting OSS = 0x00)
     * Used as a minimum wait time for pressure measurements conversion
     */
    private static final long PRESS_MEASUREMENT_MAX_CONVERSION_TIME = 5;

    @I2C(name = "i2c0")
    I2CBus i2C0Bus;

    @ConfigProperty(name = "bmp180.moduleAddress")
    int bmp180ModuleAddress;

    @ConfigProperty(name = "bmp180.pressureOversamplingRatio")
    String pressureOversamplingRatio;

    private int controlRegPressMeasurementCommand;

    @Loggable
    @PostConstruct
    public void init() {
        controlRegPressMeasurementCommand = CONTROL_REGISTER_PRESS_MEASUREMENT_DEFAULT_COMMAND
                | PressOversamplingRatio.valueOf(pressureOversamplingRatio).getOss();
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
            sleep(TEMP_MEASUREMENT_MAX_CONVERSION_TIME);
            return toSignedIntFromWord(smBus.readByteData(OUT_MSB.address()), smBus.readByteData(OUT_LSB.address()));
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
            while (StartOfConversation.IN_PROGRESS.getSco() == (smBus.readByteData(CONTROL_REGISTER) & StartOfConversation.IN_PROGRESS.getSco())) {
                sleep(PRESS_MEASUREMENT_MAX_CONVERSION_TIME);
            }
            return toSignedIntFromWord(smBus.readByteData(OUT_MSB.address()), smBus.readByteData(OUT_LSB.address()));
        } catch (Exception e) {
            throw new SensorException(E_BMP180_0001.getId(), TEMP_PRESS, BMP180, E_BMP180_0001.getName(), e);
        }
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
