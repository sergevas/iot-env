package dev.sergevas.iot.env.shared.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An array of sensors readings
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SensorReadingsType {
    private List<SensorReadingsItemType> sReadings;

    public SensorReadingsType sReadings(List<SensorReadingsItemType> sReadings) {
        this.sReadings = sReadings;
        return this;
    }

    public SensorReadingsType addSReadingsItem(SensorReadingsItemType sReadingsItem) {
        if (this.sReadings == null) {
            this.sReadings = new ArrayList<SensorReadingsItemType>();
        }
        this.sReadings.add(sReadingsItem);
        return this;
    }

    /**
     * Sensor readings
     *
     * @return sReadings
     **/
    @JsonProperty("s_readings")
    public List<SensorReadingsItemType> getSReadings() {
        return sReadings;
    }

    public void setSReadings(List<SensorReadingsItemType> sReadings) {
        this.sReadings = sReadings;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorReadingsType sensorReadingsType = (SensorReadingsType) o;
        return Objects.equals(this.sReadings, sensorReadingsType.sReadings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sReadings);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SensorReadingsType {\n");
        sb.append("    sReadings: ").append(toIndentedString(sReadings)).append("\n");
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
