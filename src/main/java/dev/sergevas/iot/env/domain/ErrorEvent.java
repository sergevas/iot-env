package dev.sergevas.iot.env.domain;

public enum ErrorEvent {
    E_BMP180_0001("E-BMP180-0001", "BMP180 data read error"),
    E_BMP180_0002("E-BMP180-0002", "BMP180 data write error"),
    E_BMP180_0003("E-BMP180-0003", "BMP180 calibration coefficients validation error"),
    E_SHT3X_0001("E-SHT3X-0001", "SHT3X data read error"),
    E_SHT3X_0002("E-SHT3X-0002", "SHT3X data write error"),
    E_BH1750_0001("E-BH1750-0001", "BH1750 data read error"),
    E_SYSTEM_0001("E-SYSTEM-0001", "CPU temperature read error"),
    E_SYSTEM_0002("E-SYSTEM-0002", "Disk space fetch error");

    private final String id;
    private final String name;

    ErrorEvent(String id, String name) {
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
