package dev.sergevas.iot.env.application.service.json;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectValue implements Component {

    private final List<Component> fields = new ArrayList<>();

    public ObjectValue addField(Component component) {
        fields.add(component);
        return this;
    }

    @Override
    public String print() {
        return "{" +
                fields.stream()
                        .map(Component::print)
                        .collect(Collectors.joining(",")) +
                "}";
    }

    @Override
    public String toString() {
        return print();
    }
}
