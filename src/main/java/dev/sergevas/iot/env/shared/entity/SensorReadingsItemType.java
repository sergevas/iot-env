package dev.sergevas.iot.env.shared.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * A structure, containing the BME280 sensor data readings, e.g. temperature, humidity, pressure, etc.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SensorReadingsItemType {

    private String sType;
    private String sId;
    private String sName;
    private String sData;
    private OffsetDateTime sTimestamp;

    public SensorReadingsItemType sType(String sType) {
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

    public SensorReadingsItemType sId(String sId) {
        this.sId = sId;
        return this;
    }

    /**
     * Sensor id
     *
     * @return sId
     **/
    @JsonProperty("s_id")
    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public SensorReadingsItemType sName(String sName) {
        this.sName = sName;
        return this;
    }

    /**
     * Sensor name
     *
     * @return sName
     **/
    @JsonProperty("s_name")
    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public SensorReadingsItemType sData(String sData) {
        this.sData = sData;
        return this;
    }

    /**
     * A sensor readings value
     *
     * @return sData
     **/
    @JsonProperty("s_data")
    public String getSData() {
        return sData;
    }

    public void setSData(String sData) {
        this.sData = sData;
    }

    public SensorReadingsItemType sTimestamp(OffsetDateTime sTimestamp) {
        this.sTimestamp = sTimestamp;
        return this;
    }

    /**
     * Readings fetch timestamp
     *
     * @return sTimestamp
     **/
    @JsonProperty("s_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public OffsetDateTime getSTimestamp() {
        return sTimestamp;
    }

    public void setSTimestamp(OffsetDateTime sTimestamp) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("class SensorReadingsItemType {\n");
        sb.append("    sType: ").append(toIndentedString(sType)).append("\n");
        sb.append("    sId: ").append(toIndentedString(sId)).append("\n");
        sb.append("    sName: ").append(toIndentedString(sName)).append("\n");
        sb.append("    sData: ").append(toIndentedString(sData)).append("\n");
        sb.append("    sTimestamp: ").append(toIndentedString(sTimestamp)).append("\n");
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
