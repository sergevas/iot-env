package dev.sergevas.iot.env.domain.bmp180;

import java.util.StringJoiner;

public record Bmp180Temperature(long tenthDeg) {

    public double degrees() {
        return tenthDeg / 10.0;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bmp180Temperature.class.getSimpleName() + "[", "]")
                .add("tenthDeg=" + tenthDeg)
                .add(" degrees=" + degrees())
                .toString();
    }
}
