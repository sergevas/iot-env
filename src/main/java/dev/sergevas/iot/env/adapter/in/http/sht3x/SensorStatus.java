package dev.sergevas.iot.env.adapter.in.http.sht3x;

import dev.sergevas.iot.env.domain.sht3x.StatusRegister;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.Valid;

import java.util.Objects;
import java.util.StringJoiner;

public class SensorStatus {

    private @Valid String writeDataChecksumStatus;
    private @Valid String commandStatus;
    private @Valid String systemResetDetected;
    private @Valid String tTrackingAlert;
    private @Valid String rhTrackingAlert;
    private @Valid String heaterStatus;
    private @Valid String alertPendingStatus;

    public SensorStatus writeDataChecksumStatus(String writeDataChecksumStatus) {
        this.writeDataChecksumStatus = writeDataChecksumStatus;
        return this;
    }

    @JsonbProperty("write_data_checksum_status")
    public String getWriteDataChecksumStatus() {
        return writeDataChecksumStatus;
    }

    public SensorStatus commandStatus(String commandStatus) {
        this.commandStatus = commandStatus;
        return this;
    }

    @JsonbProperty("command_status")
    public String getCommandStatus() {
        return commandStatus;
    }

    public SensorStatus systemResetDetected(String systemResetDetected) {
        this.systemResetDetected = systemResetDetected;
        return this;
    }

    @JsonbProperty("system_reset_detected")
    public String getSystemResetDetected() {
        return systemResetDetected;
    }

    public SensorStatus tTrackingAlert(String tTrackingAlert) {
        this.tTrackingAlert = tTrackingAlert;
        return this;
    }

    @JsonbProperty("t_tracking_alert")
    public String getTTrackingAlert() {
        return tTrackingAlert;
    }

    public SensorStatus rhTrackingAlert(String rhTrackingAlert) {
        this.rhTrackingAlert = rhTrackingAlert;
        return this;
    }

    @JsonbProperty("rh_tracking_alert")
    public String getRhTrackingAlert() {
        return rhTrackingAlert;
    }

    public SensorStatus heaterStatus(String heaterStatus) {
        this.heaterStatus = heaterStatus;
        return this;
    }

    @JsonbProperty("heater_status")
    public String getHeaterStatus() {
        return heaterStatus;
    }

    public SensorStatus alertPendingStatus(String alertPendingStatus) {
        this.alertPendingStatus = alertPendingStatus;
        return this;
    }

    @JsonbProperty("alert_pending_status")
    public String getAlertPendingStatus() {
        return alertPendingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SensorStatus sensorStatus = (SensorStatus) o;
        return Objects.equals(writeDataChecksumStatus, sensorStatus.writeDataChecksumStatus)
                && Objects.equals(commandStatus, sensorStatus.commandStatus)
                && Objects.equals(systemResetDetected, sensorStatus.systemResetDetected)
                && Objects.equals(tTrackingAlert, sensorStatus.tTrackingAlert)
                && Objects.equals(rhTrackingAlert, sensorStatus.rhTrackingAlert)
                && Objects.equals(heaterStatus, sensorStatus.heaterStatus)
                && Objects.equals(alertPendingStatus, sensorStatus.alertPendingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writeDataChecksumStatus, commandStatus, systemResetDetected,
                tTrackingAlert, rhTrackingAlert, heaterStatus, alertPendingStatus);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SensorStatus.class.getSimpleName() + "[", "]")
                .add("writeDataChecksumStatus='" + writeDataChecksumStatus + "'")
                .add("commandStatus='" + commandStatus + "'")
                .add("systemResetDetected='" + systemResetDetected + "'")
                .add("tTrackingAlert='" + tTrackingAlert + "'")
                .add("rhTrackingAlert='" + rhTrackingAlert + "'")
                .add("heaterStatus='" + heaterStatus + "'")
                .add("alertPendingStatus='" + alertPendingStatus + "'")
                .toString();
    }

    public static SensorStatus toSensorStatus(StatusRegister statusRegister) {
        return new SensorStatus()
                .writeDataChecksumStatus(statusRegister.writeDataChecksumStatus())
                .commandStatus(statusRegister.commandStatus())
                .systemResetDetected(statusRegister.systemResetDetected())
                .tTrackingAlert(statusRegister.tTrackingAlert())
                .rhTrackingAlert(statusRegister.rhTrackingAlert())
                .heaterStatus(statusRegister.heaterStatus())
                .alertPendingStatus(statusRegister.alertPendingStatus());
    }
}
