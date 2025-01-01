package dev.sergevas.iot.env.adapter.out.i2c;

public class RawDataConvertor {

    public static int toUnsignedInt(byte[] data) {
        int result = 0;
        for (byte datum : data) {
            result = (result << 8) | Byte.toUnsignedInt(datum);
        }
        return result;
    }
}
