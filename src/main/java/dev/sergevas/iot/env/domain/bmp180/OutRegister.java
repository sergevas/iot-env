package dev.sergevas.iot.env.domain.bmp180;

import io.quarkus.logging.Log;

import java.util.StringJoiner;

import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toSignedIntFromWord;
import static dev.sergevas.iot.env.application.service.shared.StringUtil.toHexString;

public class OutRegister {

    /*  BST-BMP180-DS000-12 4. Global Memory Map
       | Register  | Address  |
       |  OUT_MSB  |   0xF6   |
       |  OUT_LSB  |   0xF7   |
       |  OUT_XLSB |   0xF8   |
   */

    public enum OutRegisterAddress {

        OUT_MSB(0xF6),
        OUT_LSB(0xF7),
        OUT_XLSB(0xF8);

        private final int address;

        OutRegisterAddress(int address) {
            this.address = address;
        }

        public int address() {
            return address;
        }
    }

    private int msb;
    private int lsb;
    private int xlsb;
    private PressOversamplingRatio pressOversamplingRatio;

    public OutRegister msb(int msb) {
        this.msb = msb;
        return this;
    }

    public OutRegister lsb(int lsb) {
        this.lsb = lsb;
        return this;
    }

    public OutRegister xlsb(int xlsb) {
        this.xlsb = xlsb;
        return this;
    }

    public OutRegister pressOversamplingRatio(PressOversamplingRatio pressOversamplingRatio) {
        this.pressOversamplingRatio = pressOversamplingRatio;
        return this;
    }

    public int toUT() {
        Log.debugf("Enter toUT() %s", this);
        return toSignedIntFromWord(msb, lsb);
    }

    public int toUP() {
        Log.debugf("Enter toUP() %s", this);
        return ((msb & 0xFF) << 16 | (lsb & 0xFF) << 8 | xlsb & 0xFF) >> (8 - pressOversamplingRatio.getOss());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OutRegister.class.getSimpleName() + "[", "]")
                .add("msb=" + toHexString(msb))
                .add("lsb=" + toHexString(lsb))
                .add("xlsb=" + toHexString(xlsb))
                .add("pressOversamplingRatio=" + pressOversamplingRatio)
                .toString();
    }
}
