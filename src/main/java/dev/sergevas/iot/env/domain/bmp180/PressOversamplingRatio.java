package dev.sergevas.iot.env.domain.bmp180;

public enum PressOversamplingRatio {

    // BST-BMP180-DS000-12 Table 3, Column `Conversion time pressure max. [ms]`
    SINGLE(0x00, 0),
    TWO_TIMES(0x01, 30),
    FOUR_TIMES(0x02, 45),
    EIGHT_TIMES(0x03, 70);

    private final int oss;
    private final int conversionTimeCoefficient;

    PressOversamplingRatio(int oss, int conversionTimeCoefficient) {
        this.oss = oss;
        this.conversionTimeCoefficient = conversionTimeCoefficient;
    }

    public int getOss() {
        return oss;
    }

    public long getConversionTime() {
        /*
         * BST-BMP180-DS000-12 Table 3, Table 8
         * 4_500_000 nanos == 4.5 mills - max conversion time for Ultra Low Power Mode (oversampling setting OSS = 0x00)
         * Used as a minimum wait time for pressure measurements conversion
         */
        return 4_500_000L + 100_000L * oss * conversionTimeCoefficient;
    }
}
