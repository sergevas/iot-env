package dev.sergevas.iot.env.shared.application.service.json;

import java.util.Objects;
import java.util.Optional;

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
        return Optional.ofNullable(value).map(Objects::toString).orElse(null);
    }
}
