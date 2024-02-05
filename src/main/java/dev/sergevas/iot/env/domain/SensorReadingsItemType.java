package dev.sergevas.iot.env.domain;

import dev.sergevas.iot.env.application.service.json.Field;
import dev.sergevas.iot.env.application.service.json.ObjectValue;
import dev.sergevas.iot.env.application.service.json.Value;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A structure, containing the BME280 sensor data readings, e.g. temperature,
 * humidity, pressure, etc.
 **/

public class SensorReadingsItemType {
    private String sType;
    private String sId;
    private String sName;
    private String sData;
    private OffsetDateTime sTimestamp;

    /**
     * A sensor type, e.g. TEMP (temperature)
     **/
    public SensorReadingsItemType sType(String sType) {
        this.sType = sType;
        return this;
    }

    // @JsonbProperty("s_type")
    public String getsType() {
        return sType;
    }

    // @JsonbProperty("s_type")
    public void setsType(String sType) {
        this.sType = sType;
    }

    /**
     * Sensor id
     **/
    public SensorReadingsItemType sId(String sId) {
        this.sId = sId;
        return this;
    }

    // @JsonbProperty("s_id")
    public String getsId() {
        return sId;
    }

    // @JsonbProperty("s_id")
    public void setsId(String sId) {
        this.sId = sId;
    }

    /**
     * Sensor name
     **/
    public SensorReadingsItemType sName(String sName) {
        this.sName = sName;
        return this;
    }

    // @JsonbProperty("s_name")
    public String getsName() {
        return sName;
    }

    // @JsonbProperty("s_name")
    public void setsName(String sName) {
        this.sName = sName;
    }

    /**
     * A sensor readings value
     **/
    public SensorReadingsItemType sData(String sData) {
        this.sData = sData;
        return this;
    }

    // @JsonbProperty("s_data")
    // @NotNull
    public String getsData() {
        return sData;
    }

    // @JsonbProperty("s_data")
    public void setsData(String sData) {
        this.sData = sData;
    }

    /**
     * Readings fetch timestamp
     **/
    public SensorReadingsItemType sTimestamp(OffsetDateTime sTimestamp) {
        this.sTimestamp = sTimestamp;
        return this;
    }

    // @JsonbProperty("s_timestamp")
    // @NotNull
    public OffsetDateTime getsTimestamp() {
        return sTimestamp;
    }

    // @JsonbProperty("s_timestamp")
    public void setsTimestamp(OffsetDateTime sTimestamp) {
        this.sTimestamp = sTimestamp;
    }

    //    TODO: implement this method
    public String toJson() {
        return new ObjectValue()
                .addField(new Field("sType", Value.of(sType)))
                .addField(new Field("sId", Value.of(sId)))
                .addField(new Field("sName", Value.of(sName)))
                .addField(new Field("sData", Value.of(sData)))
                .addField(new Field("sTimestamp", Value.of(sTimestamp)))
                .print();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorReadingsItemType sensorReadingsItemType = (SensorReadingsItemType) o;
        return Objects.equals(this.sType, sensorReadingsItemType.sType) &&
                Objects.equals(this.sId, sensorReadingsItemType.sId) &&
                Objects.equals(this.sName, sensorReadingsItemType.sName) &&
                Objects.equals(this.sData, sensorReadingsItemType.sData) &&
                Objects.equals(this.sTimestamp, sensorReadingsItemType.sTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sType, sId, sName, sData, sTimestamp);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SensorReadingsItemType.class.getSimpleName() + "[", "]")
                .add("sType='" + sType + "'")
                .add("sId='" + sId + "'")
                .add("sName='" + sName + "'")
                .add("sData='" + sData + "'")
                .add("sTimestamp=" + sTimestamp)
                .toString();
    }
}
