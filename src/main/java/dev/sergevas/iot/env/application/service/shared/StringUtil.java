package dev.sergevas.iot.env.application.service.shared;

public class StringUtil {

    public static String toHexString(byte b) {
        return String.format("%02X ", b);
    }

    public static String toHexString(int i) {
        return String.format("%02X ", i);
    }

    public static void appendHexString(StringBuilder builder, byte[] bytes) {
        for (byte b : bytes) {
            builder.append(toHexString(b));
        }
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        appendHexString(sb, bytes);
        return sb.toString().trim();
    }

    public static String toHexString(int[] rowData) {
        StringBuilder sb = new StringBuilder();
        for (int rowDatum : rowData) {
            sb.append(toHexString(rowDatum));
        }
        return sb.toString().trim();
    }
}
