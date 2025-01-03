package dev.sergevas.iot.env.adapter.in.http.sht3x;

import dev.sergevas.iot.env.domain.sht3x.StatusRegister;
import jakarta.validation.Valid;

import java.util.Objects;
import java.util.StringJoiner;

public class Sht3xStatus {

    private @Valid String writeDataChecksumStatus;
    private @Valid String commandStatus;
    private @Valid String systemResetDetected;
    private @Valid String tTrackingAlert;
    private @Valid String rhTrackingAlert;
    private @Valid String heaterStatus;
    private @Valid String alertPendingStatus;

    public Sht3xStatus writeDataChecksumStatus(String writeDataChecksumStatus) {
        this.writeDataChecksumStatus = writeDataChecksumStatus;
        return this;
    }

    public String getWriteDataChecksumStatus() {
        return writeDataChecksumStatus;
    }

    public Sht3xStatus commandStatus(String commandStatus) {
        this.commandStatus = commandStatus;
        return this;
    }

    public String getCommandStatus() {
        return commandStatus;
    }

    public Sht3xStatus systemResetDetected(String systemResetDetected) {
        this.systemResetDetected = systemResetDetected;
        return this;
    }

    public String getSystemResetDetected() {
        return systemResetDetected;
    }

    public Sht3xStatus tTrackingAlert(String tTrackingAlert) {
        this.tTrackingAlert = tTrackingAlert;
        return this;
    }

    public String gettTrackingAlert() {
        return tTrackingAlert;
    }

    public Sht3xStatus rhTrackingAlert(String rhTrackingAlert) {
        this.rhTrackingAlert = rhTrackingAlert;
        return this;
    }

    public String getRhTrackingAlert() {
        return rhTrackingAlert;
    }

    public Sht3xStatus heaterStatus(String heaterStatus) {
        this.heaterStatus = heaterStatus;
        return this;
    }

    public String getHeaterStatus() {
        return heaterStatus;
    }

    public Sht3xStatus alertPendingStatus(String alertPendingStatus) {
        this.alertPendingStatus = alertPendingStatus;
        return this;
    }

    public String getAlertPendingStatus() {
        return alertPendingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sht3xStatus sht3xStatus = (Sht3xStatus) o;
        return Objects.equals(writeDataChecksumStatus, sht3xStatus.writeDataChecksumStatus)
                && Objects.equals(commandStatus, sht3xStatus.commandStatus)
                && Objects.equals(systemResetDetected, sht3xStatus.systemResetDetected)
                && Objects.equals(tTrackingAlert, sht3xStatus.tTrackingAlert)
                && Objects.equals(rhTrackingAlert, sht3xStatus.rhTrackingAlert)
                && Objects.equals(heaterStatus, sht3xStatus.heaterStatus)
                && Objects.equals(alertPendingStatus, sht3xStatus.alertPendingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writeDataChecksumStatus, commandStatus, systemResetDetected,
                tTrackingAlert, rhTrackingAlert, heaterStatus, alertPendingStatus);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Sht3xStatus.class.getSimpleName() + "[", "]")
                .add("writeDataChecksumStatus='" + writeDataChecksumStatus + "'")
                .add("commandStatus='" + commandStatus + "'")
                .add("systemResetDetected='" + systemResetDetected + "'")
                .add("tTrackingAlert='" + tTrackingAlert + "'")
                .add("rhTrackingAlert='" + rhTrackingAlert + "'")
                .add("heaterStatus='" + heaterStatus + "'")
                .add("alertPendingStatus='" + alertPendingStatus + "'")
                .toString();
    }

    public static Sht3xStatus toSensorStatus(StatusRegister statusRegister) {
        return new Sht3xStatus()
                .writeDataChecksumStatus(statusRegister.writeDataChecksumStatus())
                .commandStatus(statusRegister.commandStatus())
                .systemResetDetected(statusRegister.systemResetDetected())
                .tTrackingAlert(statusRegister.tTrackingAlert())
                .rhTrackingAlert(statusRegister.rhTrackingAlert())
                .heaterStatus(statusRegister.heaterStatus())
                .alertPendingStatus(statusRegister.alertPendingStatus());
    }
}
