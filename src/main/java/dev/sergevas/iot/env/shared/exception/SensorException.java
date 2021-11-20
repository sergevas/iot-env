package dev.sergevas.iot.env.shared.exception;

import dev.sergevas.iot.env.shared.model.SensorType;

public class SensorException extends RuntimeException {

    private String eventId;
    private SensorType sensorType;

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

    public SensorException(String eventId, SensorType sensorType, String message, Throwable cause) {
        super(message, cause);
        this.eventId = eventId;
        this.sensorType = sensorType;
    }

    public SensorException(String eventId, SensorType sensorType, String message) {
        super(message);
        this.eventId = eventId;
        this.sensorType = sensorType;
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
}
