package dev.sergevas.iot.env.shared.application.service.json;

public class Value<T> implements Component {

    private final T value;

    public Value(T value) {
        this.value = value;
    }

    public static <T> Value<T> of(T value) {
        return new Value<>(value);
    }

    @Override
    public String print() {
        return "\"" + value.toString() + "\"";
    }
}
