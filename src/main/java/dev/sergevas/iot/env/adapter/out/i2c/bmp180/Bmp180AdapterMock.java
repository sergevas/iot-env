package dev.sergevas.iot.env.adapter.out.i2c.bmp180;

import dev.sergevas.iot.env.application.port.out.BMP180Spec;
import dev.sergevas.iot.env.domain.bmp180.Calibration;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@DefaultBean
@ApplicationScoped
public class Bmp180AdapterMock implements BMP180Spec {

    private static final String CHIP_ID = "0x55";

    @Loggable(logReturnVal = true)
    @Override
    public Calibration readCalibration() {
        /*
         * BST-BMP180-DS000-12 3.5 Calculating pressure and temperature
         * Figure 4: Algorithm for pressure and temperature measurement
         */
        return new Calibration()
                .ac1(8974)
                .ac2(-1187)
                .ac3(-14677)
                .ac4(33821)
                .ac5(25550)
                .ac6(20066)
                .b1(6515)
                .b2(47)
                .mb(-32768)
                .mc(-11786)
                .md(2485);
    }

    @Loggable
    @Override
    public int readUncompensatedTemperature() {
        return 27898;
    }

    @Loggable
    @Override
    public int readUncompensatedPressure() {
        return 23843;
    }

    @Loggable(logReturnVal = true)
    @Override
    public String readChipId() {
        return CHIP_ID;
    }

    @Loggable
    @Override
    public void softReset() {
    }
}
