package dev.sergevas.iot.env.domain.bmp180;

public enum StartOfConversation {
    IN_PROGRESS(0x20),
    COMPLETE(0x00);

    private final int sco;

    StartOfConversation(int sco) {
        this.sco = sco;
    }

    public int getSco() {
        return sco;
    }
}
