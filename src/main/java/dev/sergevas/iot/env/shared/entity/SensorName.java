package dev.sergevas.iot.env.shared.entity;

public enum SensorName {
    BH1750("BH1750"),
    ORANGE_PI_ZERO("Orange Pi Zero");

    private final String name;

    SensorName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
