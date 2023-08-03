package dev.sergevas.iot.env.shared.entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * An array of sensors readings
 **/

public class SensorReadingsType {
    private @Valid List<SensorReadingsItemType> sReadings;

    /**
     * Sensor readings
     **/
    public SensorReadingsType sReadings(List<SensorReadingsItemType> sReadings) {
        this.sReadings = sReadings;
        return this;
    }


    @JsonbProperty("s_readings")
    public List<SensorReadingsItemType> getsReadings() {
        return sReadings;
    }

    @JsonbProperty("s_readings")
    public void setsReadings(List<SensorReadingsItemType> sReadings) {
        this.sReadings = sReadings;
    }

    public SensorReadingsType addSReadingsItem(SensorReadingsItemType sReadingsItem) {
        if (this.sReadings == null) {
            this.sReadings = new ArrayList<>();
        }

        this.sReadings.add(sReadingsItem);
        return this;
    }

    public SensorReadingsType removeSReadingsItem(SensorReadingsItemType sReadingsItem) {
        if (sReadingsItem != null && this.sReadings != null) {
            this.sReadings.remove(sReadingsItem);
        }

        return this;
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
        return new StringJoiner(", ", SensorReadingsType.class.getSimpleName() + "[", "]")
                .add("sReadings=" + sReadings)
                .toString();
    }
}
