package dev.sergevas.iot.env.bme280.model;

public enum Oversampling {

    OS_0((byte)0b000),
    OS_1((byte)0b001),
    OS_2((byte)0b010),
    OS_4((byte)0b011),
    OS_8((byte)0b100),
    OS_16((byte)0b101);

    private byte val;

    Oversampling(byte val) {
        this.val = val;
    }

    public byte val() {
        return val;
    }
}
