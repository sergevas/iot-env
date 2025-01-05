package dev.sergevas.iot.env.adapter.out.i2c;

import dev.sergevas.iot.env.application.service.shared.ServiceInternalError;

import java.util.Arrays;

import static java.util.Objects.isNull;

public class RawDataConvertor {

    private static final int WORD_LENGTH_BITES = 2;

    public static int toUnsignedIntFromWord(byte[] data) {
        validateWord(data);
        return toUnsignedIntFromWord(data[0], data[1]);
    }

    public static int toUnsignedIntFromWord(int msb, int lsb) {
        return (msb & 0xFF) << 8 | lsb & 0xFF;
    }

    public static int toSignedIntFromWord(byte[] data) {
        validateWord(data);
        return toSignedIntFromWord(data[0], data[1]);
    }

    public static int toSignedIntFromWord(int msb, int lsb) {
        return msb << 8 | lsb & 0xFF;
    }

    private static void validateWord(byte[] word) {
        if (isNull(word) || word.length != WORD_LENGTH_BITES) {
            throw new ServiceInternalError(String.format("Invalid raw data word [%s]. Must not be null and have length of %d bytes",
                    Arrays.toString(word), WORD_LENGTH_BITES));
        }
    }
}
