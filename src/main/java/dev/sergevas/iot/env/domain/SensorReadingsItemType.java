package dev.sergevas.iot.env.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A structure, containing the environmental sensor data readings, e.g. temperature, humidity, pressure, etc.
 **/

public class SensorReadingsItemType {
    private @Valid String sType;
    private @Valid String sId;
    private @Valid String sName;
    private @Valid String sData;
    private @Valid OffsetDateTime sTimestamp;

    /**
     * A sensor type, e.g. TEMP (temperature)
     **/
    public SensorReadingsItemType sType(String sType) {
        this.sType = sType;
        return this;
    }

    public String getsType() {
        return sType;
    }

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

    public String getsId() {
        return sId;
    }

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

    public String getsName() {
        return sName;
    }

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

    @NotNull
    public String getsData() {
        return sData;
    }

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

    @NotNull
    public OffsetDateTime getsTimestamp() {
        return sTimestamp;
    }

    public void setsTimestamp(OffsetDateTime sTimestamp) {
        this.sTimestamp = sTimestamp;
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
