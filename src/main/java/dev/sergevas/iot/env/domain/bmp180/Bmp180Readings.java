package dev.sergevas.iot.env.domain.bmp180;

public record Bmp180Readings(Bmp180Temperature temperature, Bmp180Pressure pressure, String chipId) {
}
