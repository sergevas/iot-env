package dev.sergevas.iot.env.domain;

public enum ErrorEvent {
    E_BH1750_0001("E-BH1750-0001", "BH1750 data read error"),
    E_SYSTEM_0001("E_SYSTEM_0001", "CPU temperature read error");

    private final String id;
    private final String name;

    private ErrorEvent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
