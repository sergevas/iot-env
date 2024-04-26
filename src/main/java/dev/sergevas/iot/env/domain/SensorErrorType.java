package dev.sergevas.iot.env.domain;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * This type contains a sensor readings request error description elements of the API
 **/

public class SensorErrorType {
    private @Valid String eventId;
    private @Valid String eventName;
    private @Valid String sName;
    private @Valid String sType;
    private @Valid String desc;
    private @Valid OffsetDateTime eventTimestamp;

    /**
     * An error event type id
     **/
    public SensorErrorType eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }


    @JsonbProperty("event_id")
    @NotNull
    public String getEventId() {
        return eventId;
    }

    @JsonbProperty("event_id")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * An error event descriptive name
     **/
    public SensorErrorType eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }


    @JsonbProperty("event_name")
    @NotNull
    public String getEventName() {
        return eventName;
    }

    @JsonbProperty("event_name")
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * A sensor name, e.g. TEMP (temperature)
     **/
    public SensorErrorType sName(String sName) {
        this.sName = sName;
        return this;
    }


    @JsonbProperty("s_name")
    public String getsName() {
        return sName;
    }

    @JsonbProperty("s_name")
    public void setsName(String sName) {
        this.sName = sName;
    }

    /**
     * A sensor type, e.g. BH1750 (Ambient light sensor)
     **/
    public SensorErrorType sType(String sType) {
        this.sType = sType;
        return this;
    }


    @JsonbProperty("s_type")
    public String getsType() {
        return sType;
    }

    @JsonbProperty("s_type")
    public void setsType(String sType) {
        this.sType = sType;
    }

    /**
     * An error event detailed info
     **/
    public SensorErrorType desc(String desc) {
        this.desc = desc;
        return this;
    }


    @JsonbProperty("desc")
    public String getDesc() {
        return desc;
    }

    @JsonbProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * An error event occurrence timestamp
     **/
    public SensorErrorType eventTimestamp(OffsetDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
        return this;
    }


    @JsonbProperty("event_timestamp")
    @NotNull
    public OffsetDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    @JsonbProperty("event_timestamp")
    public void setEventTimestamp(OffsetDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorErrorType sensorErrorType = (SensorErrorType) o;
        return Objects.equals(this.eventId, sensorErrorType.eventId) &&
                Objects.equals(this.eventName, sensorErrorType.eventName) &&
                Objects.equals(this.sName, sensorErrorType.sName) &&
                Objects.equals(this.sType, sensorErrorType.sType) &&
                Objects.equals(this.desc, sensorErrorType.desc) &&
                Objects.equals(this.eventTimestamp, sensorErrorType.eventTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, sName, sType, desc, eventTimestamp);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SensorErrorType.class.getSimpleName() + "[", "]")
                .add("eventId='" + eventId + "'")
                .add("eventName='" + eventName + "'")
                .add("sName='" + sName + "'")
                .add("sType='" + sType + "'")
                .add("desc='" + desc + "'")
                .add("eventTimestamp=" + eventTimestamp)
                .toString();
    }
}
