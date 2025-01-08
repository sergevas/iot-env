package dev.sergevas.iot.env.domain.bmp180;

import java.util.StringJoiner;

public record Bmp180Pressure(long pa) {

    public double mmHg() {
        return pa / 133.3223684;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bmp180Pressure.class.getSimpleName() + "[", "]")
                .add("pa=" + pa)
                .add(" mmHg=" + mmHg())
                .toString();
    }
}
