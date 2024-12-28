package dev.sergevas.iot.env.adapter.out.i2c.sht3x;

public record Sht3xRowReadings(int temperature, byte temperatureCrc, int humidity, byte humidityCrc) {
}
