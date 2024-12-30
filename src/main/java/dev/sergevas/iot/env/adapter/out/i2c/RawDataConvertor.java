package dev.sergevas.iot.env.adapter.out.i2c;

public class RawDataConvertor {

    public static int toUnsignedInt(byte[] data) {
        int result = 0;
        for (int i = 0; i < data.length; i++) {
            result = (result << 8) | Byte.toUnsignedInt(data[i]);
        }
        return result;
    }
}
