package dev.sergevas.iot.env.domain.bmp180;

public record Bmp180Readings(double temperature, double pressure, String chipId) {
}
