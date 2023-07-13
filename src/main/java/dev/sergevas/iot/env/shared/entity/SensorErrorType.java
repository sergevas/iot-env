package dev.sergevas.iot.env.shared.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * This type contains a sensor readings request error description elements of the API
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SensorErrorType {

    private String eventId;
    private String eventName;
    private String sType;
    private String desc;
    private OffsetDateTime eventTimestamp;

    public SensorErrorType eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    /**
     * An error event type id
     *
     * @return eventId
     **/
    @JsonProperty("event_id")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public SensorErrorType eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    /**
     * An error event descriptive name
     *
     * @return eventName
     **/
    @JsonProperty("event_name")
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public SensorErrorType sType(String sType) {
        this.sType = sType;
        return this;
    }

    /**
     * A sensor type, e.g. TEMP (temperature)
     *
     * @return sType
     **/
    @JsonProperty("s_type")
    public String getSType() {
        return sType;
    }

    public void setSType(String sType) {
        this.sType = sType;
    }

    public SensorErrorType desc(String desc) {
        this.desc = desc;
        return this;
    }

    /**
     * An error event detailed info
     *
     * @return desc
     **/
    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public SensorErrorType eventTimestamp(OffsetDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
        return this;
    }

    /**
     * An error event occurrence timestamp
     *
     * @return eventTimestamp
     **/
    @JsonProperty("event_timestamp")
    public OffsetDateTime getEventTimestamp() {
        return eventTimestamp;
    }

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
                Objects.equals(this.sType, sensorErrorType.sType) &&
                Objects.equals(this.desc, sensorErrorType.desc) &&
                Objects.equals(this.eventTimestamp, sensorErrorType.eventTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, sType, desc, eventTimestamp);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SensorErrorType {\n");

        sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
        sb.append("    eventName: ").append(toIndentedString(eventName)).append("\n");
        sb.append("    sType: ").append(toIndentedString(sType)).append("\n");
        sb.append("    desc: ").append(toIndentedString(desc)).append("\n");
        sb.append("    eventTimestamp: ").append(toIndentedString(eventTimestamp)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
