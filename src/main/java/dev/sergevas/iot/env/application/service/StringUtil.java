package dev.sergevas.iot.env.application.service;

public class StringUtil {

    public static void appendHexString(StringBuilder builder, byte[] bytes) {
        for (byte b : bytes) {
            builder.append(String.format("%02X ", b));
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
            sb.append(String.format("%02X ", rowDatum));
        }
        return sb.toString().trim();
    }
}
