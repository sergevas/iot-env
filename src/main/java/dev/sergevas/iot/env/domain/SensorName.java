package dev.sergevas.iot.env.domain;

public enum SensorName {
    BH1750("BH1750"),
    BMP180("BMP180"),
    SHT3x("SHT3x"),
    RASPBERRY_PI_ZERO("Raspberry Pi Zero"),
    RASPBERRY_PI_ZERO_2("Raspberry Pi Zero 2"),
    ORANGE_PI_ZERO("Orange Pi Zero");

    private final String name;

    SensorName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
