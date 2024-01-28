package dev.sergevas.iot.env.shared.application.port.out;


import dev.sergevas.iot.env.shared.domain.SensorName;
import dev.sergevas.iot.env.shared.domain.SensorType;

public class SensorException extends RuntimeException {

    private String eventId;
    private SensorType sensorType;
    private SensorName sensorName;

    public SensorException() {
    }

    public SensorException(String message) {
        super(message);
    }

    public SensorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SensorException(Throwable cause) {
        super(cause);
    }

    public SensorException(String eventId, SensorType sensorType, SensorName sensorName, String message, Throwable cause) {
        super(message, cause);
        this.eventId = eventId;
        this.sensorType = sensorType;
        this.sensorName = sensorName;
    }

    public SensorException(String eventId, SensorType sensorType, SensorName sensorName, String message) {
        super(message);
        this.eventId = eventId;
        this.sensorType = sensorType;
        this.sensorName = sensorName;
    }


    public SensorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getEventId() {
        return eventId;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public SensorName getSensorName() {
        return sensorName;
    }
}
