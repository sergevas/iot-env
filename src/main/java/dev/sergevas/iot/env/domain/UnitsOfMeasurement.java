package dev.sergevas.iot.env.domain;

public enum UnitsOfMeasurement {

    CELSIUS("celsius"),
    LUX("lx"),
    PA("pa"),
    MM_HG("mm/hg"),
    PERCENT("%");

    private final String units;

    UnitsOfMeasurement(String units) {
        this.units = units;
    }

    public String getUnits() {
        return units;
    }
}
