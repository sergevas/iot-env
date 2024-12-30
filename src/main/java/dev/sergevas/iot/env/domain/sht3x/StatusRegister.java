package dev.sergevas.iot.env.domain.sht3x;

import java.util.Objects;
import java.util.StringJoiner;

public class StatusRegister {

    private static final String UNDEFINED = "undefined";

    private final byte writeDataChecksumStatus;
    private final byte commandStatus;
    private final byte systemResetDetected;
    private final byte tTrackingAlert;
    private final byte rhTrackingAlert;
    private final byte heaterStatus;
    private final byte alertPendingStatus;

    public StatusRegister(byte writeDataChecksumStatus,
                          byte commandStatus,
                          byte systemResetDetected,
                          byte tTrackingAlert,
                          byte rhTrackingAlert,
                          byte heaterStatus,
                          byte alertPendingStatus) {
        this.writeDataChecksumStatus = writeDataChecksumStatus;
        this.commandStatus = commandStatus;
        this.systemResetDetected = systemResetDetected;
        this.tTrackingAlert = tTrackingAlert;
        this.rhTrackingAlert = rhTrackingAlert;
        this.heaterStatus = heaterStatus;
        this.alertPendingStatus = alertPendingStatus;
    }

    public String writeDataChecksumStatus() {
        return switch (writeDataChecksumStatus) {
            case 0 -> "LAST_WRITE_CORRECT";
            case 1 -> "LAST_WRITE_FAILED";
            default -> UNDEFINED;
        };
    }

    public String commandStatus() {
        return switch (commandStatus) {
            case 0 -> "LAST_COMMAND_SUCCESS";
            case 1 -> "LAST_COMMAND_NOT_PROCESSED";
            default -> UNDEFINED;
        };
    }

    public String systemResetDetected() {
        return switch (systemResetDetected) {
            case 0 -> "NO_RESET";
            case 1 -> "RESET";
            default -> UNDEFINED;
        };
    }

    public String tTrackingAlert() {
        return switch (tTrackingAlert) {
            case 0 -> "NO_ALERT";
            case 1 -> "ALERT";
            default -> UNDEFINED;
        };
    }

    public String rhTrackingAlert() {
        return switch (rhTrackingAlert) {
            case 0 -> "NO_ALERT";
            case 1 -> "ALERT";
            default -> UNDEFINED;
        };
    }

    public String heaterStatus() {
        return switch (heaterStatus) {
            case 0 -> "OFF";
            case 1 -> "ON";
            default -> UNDEFINED;
        };
    }

    public String alertPendingStatus() {
        return switch (alertPendingStatus) {
            case 0 -> "NO_PENDING_ALERT";
            case 1 -> "AT_LEAST_ONE_PENDING_ALERT";
            default -> UNDEFINED;
        };
    }

    public static StatusRegister fromRawData(byte[] statusRegisterReadings) {
        return new StatusRegister(
                (byte) (0x01 & statusRegisterReadings[1]),
                (byte) ((0x02 & statusRegisterReadings[1]) >> 1),
                (byte) ((0x10 & statusRegisterReadings[1]) >> 4),
                (byte) ((0x04 & statusRegisterReadings[0]) >> 2),
                (byte) ((0x08 & statusRegisterReadings[0]) >> 3),
                (byte) ((0x20 & statusRegisterReadings[0]) >> 5),
                (byte) ((0x80 & statusRegisterReadings[0]) >> 7));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StatusRegister that = (StatusRegister) o;
        return writeDataChecksumStatus == that.writeDataChecksumStatus
                && commandStatus == that.commandStatus
                && systemResetDetected == that.systemResetDetected
                && tTrackingAlert == that.tTrackingAlert
                && rhTrackingAlert == that.rhTrackingAlert
                && heaterStatus == that.heaterStatus
                && alertPendingStatus == that.alertPendingStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(writeDataChecksumStatus, commandStatus, systemResetDetected, tTrackingAlert,
                rhTrackingAlert, heaterStatus, alertPendingStatus);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StatusRegister.class.getSimpleName() + "[", "]")
                .add("writeDataChecksumStatus=" + writeDataChecksumStatus)
                .add("commandStatus=" + commandStatus)
                .add("systemResetDetected=" + systemResetDetected)
                .add("tTrackingAlert=" + tTrackingAlert)
                .add("rhTrackingAlert=" + rhTrackingAlert)
                .add("heaterStatus=" + heaterStatus)
                .add("alertPendingStatus=" + alertPendingStatus)
                .toString();
    }
}
