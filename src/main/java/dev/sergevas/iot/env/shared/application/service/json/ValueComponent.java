package dev.sergevas.iot.env.shared.application.service.json;

public class ValueComponent<T> implements Component {

    private final T value;

    public ValueComponent(T value) {
        this.value = value;
    }

    public static <T> ValueComponent<T> of(T value) {
        return new ValueComponent<>(value);
    }

    @Override
    public String print() {
        return value.toString();
    }
}
