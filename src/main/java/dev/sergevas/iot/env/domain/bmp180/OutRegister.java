package dev.sergevas.iot.env.domain.bmp180;

import static dev.sergevas.iot.env.adapter.out.i2c.RawDataConvertor.toSignedIntFromWord;

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
        return toSignedIntFromWord(msb, lsb);
    }

    public int toUP() {
        return (msb << 16 | (lsb & 0xFF) << 8 | xlsb & 0xFF) >> (8 - pressOversamplingRatio.getOss());
    }
}
