package dev.sergevas.iot.env.bh1750.domain;

import java.time.OffsetDateTime;
import java.util.StringJoiner;

public record Bh1750Readings(double lightIntensity, OffsetDateTime lightIntensityTimestamp) {

    @Override
    public String toString() {
        return new StringJoiner(", ", Bh1750Readings.class.getSimpleName() + "[", "]")
                .add("lightIntensity=" + lightIntensity)
                .add("lightIntensityTimestamp=" + lightIntensityTimestamp)
                .toString();
    }
}
