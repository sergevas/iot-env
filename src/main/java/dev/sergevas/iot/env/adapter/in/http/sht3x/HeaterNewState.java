package dev.sergevas.iot.env.adapter.in.http.sht3x;

public record HeaterNewState(State state) {

    public enum State {
        ON, OFF
    }
}
